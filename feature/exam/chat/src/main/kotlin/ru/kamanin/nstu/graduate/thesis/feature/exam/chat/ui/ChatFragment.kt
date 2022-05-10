package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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
import ru.kamanin.nstu.graduate.thesis.component.ui.core.insets.setupKeyboardInsets
import ru.kamanin.nstu.graduate.thesis.component.ui.core.snackbar.showErrorSnackbar
import ru.kamanin.nstu.graduate.thesis.component.ui.core.snackbar.showInfoSnackbar
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.databinding.FragmentChatBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.ChatState
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.ChatViewModel
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui.adapter.MessageAdapter
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.Openable
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.error.ArtefactErrorState
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

	private val writeStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
		val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
		val shouldRationale = shouldShowRequestPermissionRationale(permission)
		when {
			shouldRationale || !granted -> {
				viewModel.clearSelectedArtefact()
				showFailedPermissionDialog(permission = permission, okay = ::showSettingDialog)
			}

			granted                     -> {
				viewModel.afterStoragePermissionGranted()
			}
		}
	}

	private val selectDocumentTree = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri: Uri? ->
		if (uri != null) {
			viewModel.selectDocumentTreeDirectory(uri)
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
		adapter = MessageAdapter(
			artefactClicked = viewModel::selectArtefact,
			textClicked = viewModel::copyText
		)
		viewBinding.messageList.adapter = adapter
	}

	private fun initListeners() {
		viewBinding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
		viewBinding.inputBottomPanel.sendButton.setOnClickListener { viewModel.send() }
		viewBinding.inputBottomPanel.artefactCancel.setOnClickListener { viewModel.detachContent() }
		viewBinding.inputBottomPanel.shareButton.setOnClickListener {
			viewBinding.inputBottomPanel.messageEditText.clearFocus()
			showShareBottomSheetDialog()
		}
		setShareBottomSheetResultListener(::handleShareResult)
	}

	private fun handleShareResult(result: ShareResult) {
		when (result) {
			is ShareResult.Camera    -> viewModel.attachContent(result.uri)
			is ShareResult.Content   -> viewModel.attachContent(result.uri)
			is ShareResult.Rationale -> showFailedPermissionDialog(permission = result.permission, okay = ::showSettingDialog)
		}
	}

	private fun initObservers() {
		viewModel.message.bind(viewLifecycleOwner, viewBinding.inputBottomPanel.messageEditText)
		viewModel.state.subscribe(viewLifecycleOwner, ::renderState)
		viewModel.sendMessageEvent.subscribe(viewLifecycleOwner, ::handleSendEvent)
		viewModel.infoEvent.subscribe(viewLifecycleOwner, ::handleInfoEvent)
		viewModel.openFileEvent.subscribe(viewLifecycleOwner, ::handleOpenFileEvent)
		viewModel.attachFileEvent.subscribe(viewLifecycleOwner, ::handleFileAttachedEvent)
		viewModel.storagePermissionEvent.subscribe(viewLifecycleOwner, ::handleStoragePermissionEvent)
		viewModel.selectDocumentTreeEvent.subscribe(viewLifecycleOwner, ::handleSelectDocumentEvent)
		viewModel.artefactErrorEvent.subscribe(viewLifecycleOwner, ::handleArtefactErrorEvent)
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

	private fun handleInfoEvent(text: String) {
		showInfoSnackbar(text, R.id.inputBottomPanel)
	}

	private fun handleOpenFileEvent(openable: Openable) {
		val intent = Intent().apply {
			action = Intent.ACTION_VIEW
			addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
			addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			setDataAndType(openable.uri, openable.mimeType)
		}

		startActivity(intent)
	}

	private fun handleFileAttachedEvent(file: FileInfo?) {
		if (file != null) {
			showAttachedFile(file)
		} else {
			hideAttachedFile()
		}
	}

	private fun handleStoragePermissionEvent() {
		writeStoragePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
	}

	private fun handleSelectDocumentEvent() {
		selectDocumentTree.launch(null)
	}

	private fun handleArtefactErrorEvent(errorState: ArtefactErrorState) {
		val message = when (errorState) {
			ArtefactErrorState.DownloadError       -> getString(R.string.artefact_download_error)

			ArtefactErrorState.UploadError         -> getString(R.string.artefact_upload_error)

			ArtefactErrorState.NotFoundError       -> getString(R.string.artefact_not_found_error)

			is ArtefactErrorState.UnsupportedError -> getString(R.string.artefact_unsupported_error, errorState.file.extension)
		}
		showErrorSnackbar(message, R.id.inputBottomPanel)
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