package ru.kamanin.nstu.graduate.thesis.feature.exam.task.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.kamanin.nstu.graduate.thesis.component.ui.insets.setupKeyboardInsets
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.databinding.FragmentTaskBinding

@AndroidEntryPoint
class TaskFragment : Fragment(R.layout.fragment_task) {

	private val viewBinding: FragmentTaskBinding by viewBinding()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewBinding.setupKeyboardInsets()

		initListeners()
	}

	private fun initListeners() {
		viewBinding.toolbar.setNavigationOnClickListener {
			findNavController().popBackStack()
		}
	}
}