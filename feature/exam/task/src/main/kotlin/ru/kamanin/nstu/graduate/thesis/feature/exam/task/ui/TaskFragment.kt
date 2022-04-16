package ru.kamanin.nstu.graduate.thesis.feature.exam.task.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.subscribe
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.dialog.ShareResult
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.dialog.setShareBottomSheetResultListener
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.dialog.showShareBottomSheetDialog
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.showFailedPermissionDialog
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.showSettingDialog
import ru.kamanin.nstu.graduate.thesis.component.core.time.RemainingTime
import ru.kamanin.nstu.graduate.thesis.component.ui.insets.setupKeyboardInsets
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.databinding.FragmentTaskBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.presentation.TaskViewModel
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.TaskType

@AndroidEntryPoint
@WithFragmentBindings
class TaskFragment : Fragment(R.layout.fragment_task) {

	private val viewBinding: FragmentTaskBinding by viewBinding()
	private val viewModel: TaskViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewBinding.setupKeyboardInsets()

		initListeners()

		viewModel.remainingTimeEvent.subscribe(viewLifecycleOwner, ::showRemainingTime)
		viewBinding.taskText.text = viewModel.text.description
		viewBinding.textTheme.text = viewModel.text.theme
		val titleRes = when (viewModel.text.taskType) {
			TaskType.QUESTION -> R.string.question_title
			TaskType.EXERCISE -> R.string.exercise_title
		}
		viewBinding.toolbar.title = getString(titleRes, viewModel.text.number)
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

	private fun initListeners() {
		viewBinding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
		viewBinding.inputBottomPanel.shareButton.setOnClickListener {
			viewBinding.inputBottomPanel.messageEditText.clearFocus()
			showShareBottomSheetDialog()
		}
		setShareBottomSheetResultListener(::handleShareResult)
	}

	private fun handleShareResult(result: ShareResult) {
		when (result) {
			is ShareResult.Camera    -> Unit
			is ShareResult.Content   -> Unit
			is ShareResult.Rationale -> showFailedPermissionDialog(permission = result.permission, okay = ::showSettingDialog)
		}
	}
}