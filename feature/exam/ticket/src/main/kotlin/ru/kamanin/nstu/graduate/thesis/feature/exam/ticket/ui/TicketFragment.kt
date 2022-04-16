package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.subscribe
import ru.kamanin.nstu.graduate.thesis.component.core.error.ErrorState
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.showInformationDialog
import ru.kamanin.nstu.graduate.thesis.component.core.time.RemainingTime
import ru.kamanin.nstu.graduate.thesis.component.navigation.navigate
import ru.kamanin.nstu.graduate.thesis.component.ui.colors.colorFromAttr
import ru.kamanin.nstu.graduate.thesis.component.ui.insets.setupBaseInsets
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.databinding.FragmentTicketBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.navigation.TicketNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.presentation.TicketState
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.presentation.TicketViewModel
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.adapter.TaskAdapter
import javax.inject.Inject

@AndroidEntryPoint
@WithFragmentBindings
class TicketFragment : Fragment(R.layout.fragment_ticket), TicketViewModel.EventListener {

	private val viewBinding: FragmentTicketBinding by viewBinding()
	private val viewModel: TicketViewModel by viewModels()

	private val chatItem: MenuItem
		get() = viewBinding.toolbar.menu.findItem(R.id.item_chat) ?: throw IllegalStateException("Unknown menu item")

	@Inject
	lateinit var navigationProvider: TicketNavigationProvider

	private var adapter: TaskAdapter? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewBinding.setupBaseInsets()

		initSwipeRefresh()
		initAdapter()
		initListeners()
		initObservers()
	}

	private fun initSwipeRefresh() {
		val swipeBackgroundColor = colorFromAttr(R.attr.colorBlueVariant)
		val swipeIndicatorColor = colorFromAttr(R.attr.colorSwipeIndicatorTint)

		viewBinding.swipeRefresh.setProgressBackgroundColorSchemeColor(swipeBackgroundColor)
		viewBinding.swipeRefresh.setColorSchemeColors(swipeIndicatorColor)
	}

	private fun initAdapter() {
		adapter = TaskAdapter(viewModel::selectTask)
		viewBinding.taskList.adapter = adapter
	}

	private fun initListeners() {
		viewBinding.swipeRefresh.setOnRefreshListener {
			viewModel.refresh()
		}
		viewBinding.toolbar.setNavigationOnClickListener {
			findNavController().popBackStack()
		}
		chatItem.setOnMenuItemClickListener {
			viewModel.openChat()
			true
		}
	}

	private fun initObservers() {
		viewModel.eventDispatcher.bind(viewLifecycleOwner, this)
		viewModel.state.subscribe(viewLifecycleOwner, ::renderState)
		viewModel.remainingTimeEvent.subscribe(viewLifecycleOwner, ::showRemainingTime)
		viewModel.swipeRefreshEvent.subscribe(viewLifecycleOwner, viewBinding.swipeRefresh::setRefreshing)
	}

	private fun renderState(state: TicketState) {
		when (state) {
			TicketState.Initial,
			TicketState.Loading -> renderLoadingState()
			is TicketState.Content -> renderContentState(state)
			is TicketState.Error -> renderErrorState(state)
		}
	}

	private fun renderLoadingState() {
		viewBinding.toolbar.isVisible = false
		viewBinding.progressBar.isVisible = true
		viewBinding.timeContainer.isVisible = false
		viewBinding.taskList.isVisible = false
		viewBinding.swipeRefresh.isEnabled = false
		viewBinding.errorView.isVisible = false
		chatItem.isEnabled = true
	}

	private fun renderContentState(state: TicketState.Content) {
		adapter?.items = state.answers

		viewBinding.toolbar.isVisible = true
		viewBinding.progressBar.isVisible = false
		viewBinding.timeContainer.isVisible = true
		viewBinding.taskList.isVisible = true
		viewBinding.swipeRefresh.isEnabled = true
		viewBinding.errorView.isVisible = false
		chatItem.isEnabled = true
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

	private fun renderErrorState(state: TicketState.Error) {
		when (state.errorState) {

			ErrorState.BadParam -> {
				showInformationDialog(
					titleId = R.string.error_title_exam_start_end,
					descriptionId = R.string.error_description_start_end,
					okay = { findNavController().popBackStack() }
				)
			}

			else                -> {
				viewBinding.toolbar.isVisible = true
				viewBinding.progressBar.isVisible = false
				viewBinding.timeContainer.isVisible = false
				viewBinding.taskList.isVisible = false
				viewBinding.swipeRefresh.isEnabled = false
				chatItem.isEnabled = false

				viewBinding.errorView.errorState = state.errorState
				viewBinding.errorView.errorButtonListener = viewModel::refresh
				viewBinding.errorView.isVisible = true
			}
		}
	}

	override fun navigateToChat() {
		navigate(navigationProvider.toChat())
	}

	override fun navigateToTask(args: Bundle) {
		navigate(navigationProvider.toTask(args))
	}

	override fun navigateToSignIn() {
		navigate(navigationProvider.toSign())
	}

	override fun onDestroyView() {
		viewBinding.taskList.adapter = null
		adapter = null
		super.onDestroyView()
	}
}