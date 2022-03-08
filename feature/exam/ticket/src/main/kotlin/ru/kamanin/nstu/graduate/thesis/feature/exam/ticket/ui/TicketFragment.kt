package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.subscribe
import ru.kamanin.nstu.graduate.thesis.component.navigation.navigate
import ru.kamanin.nstu.graduate.thesis.component.ui.insets.setupBaseInsets
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.databinding.FragmentTicketBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.navigation.TicketNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.presentation.TicketState
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.presentation.TicketViewModel
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.adapter.TaskAdapter
import javax.inject.Inject

@AndroidEntryPoint
class TicketFragment : Fragment(R.layout.fragment_ticket), TicketViewModel.EventListener {

	private val viewBinding: FragmentTicketBinding by viewBinding()
	private val viewModel: TicketViewModel by viewModels()

	@Inject
	lateinit var navigationProvider: TicketNavigationProvider

	private var adapter: TaskAdapter? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewBinding.setupBaseInsets()

		initAdapter()
		initListeners()
		initObservers()
	}

	private fun initAdapter() {
		adapter = TaskAdapter(viewModel::selectTask)
		viewBinding.taskList.adapter = adapter
	}

	private fun initListeners() {
		viewBinding.toolbar.setNavigationOnClickListener {
			findNavController().popBackStack()
		}
		viewBinding.toolbar.menu.findItem(R.id.item_chat).setOnMenuItemClickListener {
			navigate(navigationProvider.toChat)
			true
		}
	}

	private fun initObservers() {
		viewModel.eventDispatcher.bind(viewLifecycleOwner, this)
		viewModel.state.subscribe(lifecycleScope, ::renderState)
	}

	private fun renderState(state: TicketState) {
		when (state) {
			TicketState.Loading    -> renderLoadingState()
			is TicketState.Content -> renderContentState(state)
		}
	}

	private fun renderLoadingState() {
		viewBinding.progressBar.isVisible = true
		viewBinding.taskList.isVisible = false
	}

	private fun renderContentState(state: TicketState.Content) {
		viewBinding.toolbar.isVisible = true
		viewBinding.progressBar.isVisible = false
		viewBinding.timeContainer.isVisible = true
		viewBinding.taskList.isVisible = true
		adapter?.items = state.taskItems
	}

	override fun navigateToChat() {
		TODO("Not yet implemented")
	}

	override fun navigateToTask() {
		navigate(navigationProvider.toTask)
	}
}