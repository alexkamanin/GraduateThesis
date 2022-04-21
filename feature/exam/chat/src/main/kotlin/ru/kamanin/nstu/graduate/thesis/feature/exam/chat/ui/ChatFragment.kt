package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.bind
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.subscribe
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.dialog.ShareResult
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.dialog.setShareBottomSheetResultListener
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.dialog.showShareBottomSheetDialog
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.showFailedPermissionDialog
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.showSettingDialog
import ru.kamanin.nstu.graduate.thesis.component.ui.insets.setupKeyboardInsets
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.databinding.FragmentChatBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.ChatState
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.ChatViewModel
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui.adapter.MessageAdapter

@AndroidEntryPoint
@WithFragmentBindings
class ChatFragment : Fragment(R.layout.fragment_chat) {

	private val viewBinding: FragmentChatBinding by viewBinding()
	private val viewModel: ChatViewModel by viewModels()

	private var adapter: MessageAdapter? = null

	private val pageUpdatedListener: () -> Unit = {
		viewBinding.messageList.isVisible = true
		viewBinding.progressBar.isVisible = false
	}

	private val positionUpdatedObserver = object : RecyclerView.AdapterDataObserver() {
		override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
			if (positionStart == 0) {
				viewBinding.messageList.smoothScrollToPosition(0)
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewBinding.setupKeyboardInsets()

		initAdapter()
		initListeners()
		initObservers()
	}

	private fun initAdapter() {
		adapter = MessageAdapter { artefact ->
			//TODO запросить разрешение на сохранение
			viewModel.openArtefact(artefact)
		}
		viewBinding.messageList.adapter = adapter
	}

	private fun initListeners() {
		viewBinding.inputBottomPanel.sendButton.setOnClickListener { viewModel.send() }
		viewBinding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
		viewBinding.inputBottomPanel.shareButton.setOnClickListener {
			viewBinding.inputBottomPanel.messageEditText.clearFocus()
			showShareBottomSheetDialog()
		}
		setShareBottomSheetResultListener(::handleShareResult)
	}

	private fun handleShareResult(result: ShareResult) {
		when (result) {
			is ShareResult.Camera    -> viewModel.shareImage(result.bitmap)
			is ShareResult.Content   -> viewModel.shareContent(result.uri)
			is ShareResult.Rationale -> showFailedPermissionDialog(permission = result.permission, okay = ::showSettingDialog)
		}
	}

	private fun initObservers() {
		viewModel.message.bind(viewLifecycleOwner, viewBinding.inputBottomPanel.messageEditText)
		viewModel.state.subscribe(viewLifecycleOwner, ::renderState)
		viewModel.sendEvent.subscribe(viewLifecycleOwner, ::handleSendEvent)
	}

	private fun renderState(state: ChatState) {
		when (state) {
			is ChatState.Initial,
			ChatState.Loading    -> {
				viewBinding.messageList.isVisible = false
				viewBinding.progressBar.isVisible = true
			}

			is ChatState.Content -> {
				lifecycleScope.launch { adapter?.submitData(state.messages) }
			}
		}
	}

	private fun handleSendEvent(unit: Unit) {
		adapter?.refresh()
		viewBinding.inputBottomPanel.messageEditText.setText("")
	}

	override fun onStart() {
		super.onStart()
		adapter?.addOnPagesUpdatedListener(pageUpdatedListener)
		adapter?.registerAdapterDataObserver(positionUpdatedObserver)
	}

	override fun onStop() {
		super.onStop()
		adapter?.removeOnPagesUpdatedListener(pageUpdatedListener)
		adapter?.unregisterAdapterDataObserver(positionUpdatedObserver)
	}

	override fun onDestroyView() {
		adapter = null
		viewBinding.messageList.adapter = null
		super.onDestroyView()
	}
}