package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
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
class ChatFragment : Fragment(R.layout.fragment_chat) {

	private val viewBinding: FragmentChatBinding by viewBinding()
	private val viewModel: ChatViewModel by viewModels()

	private var adapter: MessageAdapter? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewBinding.setupKeyboardInsets()

		initAdapter()
		initListeners()
		initObservers()
	}

	private fun initAdapter() {
		adapter = MessageAdapter()
		viewBinding.messageList.adapter = adapter
	}

	private fun initListeners() {
		viewBinding.inputBottomPanel.sendButton.setOnClickListener { viewModel.send() }
		viewBinding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
		viewBinding.inputBottomPanel.shareButton.setOnClickListener { showShareBottomSheetDialog() }
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
		viewModel.message.bind(lifecycleScope, viewBinding.inputBottomPanel.messageEditText)
		viewModel.state.subscribe(lifecycleScope, ::renderState)
	}

	private fun renderState(messageItems: ChatState) {
		if (messageItems is ChatState.Content) {
			adapter?.items = messageItems.messageItems
			viewBinding.messageList.scrollToPosition(0)
			if (messageItems.needClearMessageText) {
				viewBinding.inputBottomPanel.messageEditText.setText("")
			}
		}
	}

	override fun onDestroyView() {
		adapter = null
		viewBinding.messageList.adapter = null
		super.onDestroyView()
	}
}