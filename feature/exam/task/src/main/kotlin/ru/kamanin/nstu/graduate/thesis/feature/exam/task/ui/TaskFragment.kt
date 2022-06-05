package ru.kamanin.nstu.graduate.thesis.feature.exam.task.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch
import ru.kamanin.graduate.thesis.shared.notification.data.filter.NOTIFICATION_FILTER
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.bottom.ShareResult
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.bottom.setShareBottomSheetResultListener
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.bottom.showShareBottomSheetDialog
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.extensions.showFailedPermissionDialog
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.extensions.showInformationDialog
import ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.extensions.showSettingDialog
import ru.kamanin.nstu.graduate.thesis.component.ui.core.icons.icon
import ru.kamanin.nstu.graduate.thesis.component.ui.core.insets.setupKeyboardInsets
import ru.kamanin.nstu.graduate.thesis.component.ui.core.snackbar.showErrorSnackbar
import ru.kamanin.nstu.graduate.thesis.component.ui.core.snackbar.showInfoSnackbar
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.databinding.FragmentTaskBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.presentation.TaskState
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.presentation.TaskViewModel
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.ui.adapter.TaskDescriptionAdapter
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.ui.adapter.TaskMessageAdapter
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.Openable
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.error.ArtefactErrorState
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskType
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.bind
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.subscribe
import ru.kamanin.nstu.graduate.thesis.utils.time.RemainingTime

@AndroidEntryPoint
@WithFragmentBindings
class TaskFragment : Fragment(R.layout.fragment_task) {

	private val viewBinding: FragmentTaskBinding by viewBinding()
	private val viewModel: TaskViewModel by viewModels()

	private val notificationReceiver = object : BroadcastReceiver() {

		override fun onReceive(context: Context?, intent: Intent?) {
			Log.d("TEST_TECH", intent.toString())
			messagesAdapter?.refresh()
		}
	}

	private var concatAdapter: ConcatAdapter? = null
	private var messagesAdapter: TaskMessageAdapter? = null
	private var descriptionAdapter: TaskDescriptionAdapter? = null

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

		viewModel.loadMessages()
	}

	private fun initAdapter() {
		messagesAdapter = TaskMessageAdapter(
			artefactClicked = viewModel::selectArtefact,
			textClicked = viewModel::copyText
		)
		concatAdapter = ConcatAdapter(messagesAdapter)

		viewBinding.taskList.adapter = concatAdapter
	}

	@SuppressLint("ClickableViewAccessibility")
	private fun initListeners() {
		viewBinding.inputBottomPanel.messageEditText.setOnTouchListener { _, motionEvent ->
			if (motionEvent.action == MotionEvent.ACTION_DOWN) {
				viewModel.startInput()
			}
			false
		}
		viewBinding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
		viewBinding.inputBottomPanel.sendButton.setOnClickListener { viewModel.send() }
		viewBinding.inputBottomPanel.artefactCancel.setOnClickListener { viewModel.detachContent() }
		viewBinding.inputBottomPanel.shareButton.setOnClickListener {
			viewBinding.inputBottomPanel.messageEditText.clearFocus()
			showShareBottomSheetDialog()
		}
		setShareBottomSheetResultListener(::handleShareResult)
	}

	private fun initObservers() {
		viewModel.sendingForbidden.subscribe(viewLifecycleOwner, ::handleSendingForbiddenEvent)
		viewModel.message.bind(viewLifecycleOwner, viewBinding.inputBottomPanel.messageEditText)
		viewModel.state.subscribe(viewLifecycleOwner, ::renderState)
		viewModel.sendMessageEvent.subscribe(viewLifecycleOwner, ::handleSendEvent)
		viewModel.infoEvent.subscribe(viewLifecycleOwner, ::handleInfoEvent)
		viewModel.openFileEvent.subscribe(viewLifecycleOwner, ::handleOpenFileEvent)
		viewModel.attachFileEvent.subscribe(viewLifecycleOwner, ::handleFileAttachedEvent)
		viewModel.artefactErrorEvent.subscribe(viewLifecycleOwner, ::handleArtefactErrorEvent)
		viewModel.storagePermissionEvent.subscribe(viewLifecycleOwner, ::handleStoragePermissionEvent)
		viewModel.selectDocumentTreeEvent.subscribe(viewLifecycleOwner, ::handleSelectDocumentEvent)
		viewModel.remainingTimeEvent.subscribe(viewLifecycleOwner, ::showRemainingTime)
	}

	private fun handleSendingForbiddenEvent(forbidden: Boolean) {
		viewBinding.inputBottomPanel.messageEditText.isFocusable = false
		viewBinding.inputBottomPanel.shareButton.isEnabled = false
		viewBinding.inputBottomPanel.sendButton.isEnabled = false

		if (forbidden) {
			showInformationDialog(
				titleId = R.string.sending_forbidden_title,
				descriptionId = R.string.sending_forbidden_description
			)
		}
	}

	private fun showRemainingTime(time: RemainingTime) {
		val (hours, minutes, seconds) = time

		viewBinding.timeText.text =
			getString(
				R.string.card_time_text,
				hours / 10,
				hours % 10,
				minutes / 10,
				minutes % 10,
				seconds / 10,
				seconds % 10
			)
	}

	private fun renderState(state: TaskState) {
		applyTitle(state.task)
		applyDescription(state.task)
		applyMessages(state.messages)
	}

	private fun handleSendEvent() {
		messagesAdapter?.refresh()
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

		try {
			startActivity(intent)
		} catch (openableException: ActivityNotFoundException) {
			showInformationDialog(
				titleId = R.string.error_title_not_found_file_application,
				descriptionId = R.string.error_description_not_found_file_application,
			)
		}
	}

	private fun handleFileAttachedEvent(file: FileInfo?) {
		if (file != null) {
			showAttachedFile(file)
		} else {
			hideAttachedFile()
		}
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

	private fun handleStoragePermissionEvent() {
		writeStoragePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
	}

	private fun handleSelectDocumentEvent() {
		selectDocumentTree.launch(null)
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

	private fun applyTitle(task: Task) {
		val titleRes = when (task.taskType) {
			TaskType.QUESTION -> R.string.question_title
			TaskType.EXERCISE -> R.string.exercise_title
		}
		viewBinding.toolbar.title = getString(titleRes, task.number)
	}

	private fun applyDescription(task: Task) {
		if (descriptionAdapter == null) {
			descriptionAdapter = TaskDescriptionAdapter(
				task = task,
				textClicked = viewModel::copyText
			)
			concatAdapter?.addAdapter(0, requireNotNull(descriptionAdapter))
		}
	}

	private fun applyMessages(messages: PagingData<MessageItem>) {
		lifecycleScope.launch { messagesAdapter?.submitData(messages) }
	}

	private fun handleShareResult(result: ShareResult) {
		when (result) {
			is ShareResult.Camera    -> viewModel.attachContent(result.uri)
			is ShareResult.Content   -> viewModel.attachContent(result.uri)
			is ShareResult.Rationale -> showFailedPermissionDialog(permission = result.permission, okay = ::showSettingDialog)
		}
	}

	override fun onDestroyView() {
		viewBinding.taskList.adapter = null
		concatAdapter = null
		messagesAdapter = null
		descriptionAdapter = null
		super.onDestroyView()
	}

	override fun onResume() {
		super.onResume()
		requireActivity().registerReceiver(notificationReceiver, NOTIFICATION_FILTER)
	}

	override fun onPause() {
		super.onPause()
		requireActivity().unregisterReceiver(notificationReceiver)
	}
}