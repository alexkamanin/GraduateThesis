package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
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
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.bottom.ShareResult
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.bottom.setShareBottomSheetResultListener
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.bottom.showShareBottomSheetDialog
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.extensions.showFailedPermissionDialog
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.extensions.showSettingDialog
import ru.kamanin.nstu.graduate.thesis.component.ui.core.icons.icon
import ru.kamanin.nstu.graduate.thesis.component.ui.insets.setupKeyboardInsets
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.databinding.FragmentChatBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.ChatState
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.ChatViewModel
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui.adapter.MessageAdapter
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.bind
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.subscribe

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
		viewBinding.inputBottomPanel.artefactCancel.setOnClickListener { viewModel.detachFile() }
		viewBinding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
		viewBinding.inputBottomPanel.shareButton.setOnClickListener {
			viewBinding.inputBottomPanel.messageEditText.clearFocus()
			showShareBottomSheetDialog()
		}
		setShareBottomSheetResultListener(::handleShareResult)
	}

	private fun handleShareResult(result: ShareResult) {
		when (result) {
			is ShareResult.Camera    -> viewModel.attachImage(result.bitmap)
			is ShareResult.Content   -> viewModel.attachContent(result.uri)
			is ShareResult.Rationale -> showFailedPermissionDialog(permission = result.permission, okay = ::showSettingDialog)
		}
	}

	private fun initObservers() {
		viewModel.message.bind(viewLifecycleOwner, viewBinding.inputBottomPanel.messageEditText)
		viewModel.state.subscribe(viewLifecycleOwner, ::renderState)
		viewModel.sendEvent.subscribe(viewLifecycleOwner, ::handleSendEvent)
		viewModel.fileAttachedEvent.subscribe(viewLifecycleOwner, ::handleAttachedFile)
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

	private fun handleSendEvent() {
		adapter?.refresh()
		viewBinding.inputBottomPanel.messageEditText.setText("")
		viewBinding.inputBottomPanel.artefactContainer.isVisible = false
	}

	private fun handleAttachedFile(file: FileInfo?) {
		if (file != null) {
			showAttachedFile(file)
		} else {
			hideAttachedFile()
		}
	}

	private fun showAttachedFile(file: FileInfo) {
		val fileIcon = ContextCompat.getDrawable(requireContext(), file.icon)

		viewBinding.inputBottomPanel.artefactIcon.setImageDrawable(fileIcon)
		viewBinding.inputBottomPanel.artefactName.text = file.fullName
		viewBinding.inputBottomPanel.artefactContainer.isVisible = true
	}

	private fun hideAttachedFile() {
		viewBinding.inputBottomPanel.artefactIcon.setImageDrawable(null)
		viewBinding.inputBottomPanel.artefactName.text = ""
		viewBinding.inputBottomPanel.artefactContainer.isVisible = false
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