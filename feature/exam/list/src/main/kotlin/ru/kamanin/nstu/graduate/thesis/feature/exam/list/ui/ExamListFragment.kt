package ru.kamanin.nstu.graduate.thesis.feature.exam.list.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import ru.kamanin.nstu.graduate.thesis.component.navigation.navigate
import ru.kamanin.nstu.graduate.thesis.component.ui.core.colors.colorFromAttr
import ru.kamanin.nstu.graduate.thesis.component.ui.insets.setupBaseInsets
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.databinding.FragmentExamListBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.navigation.ExamListNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation.ExamListState
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation.ExamListViewModel
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation.model.ExamFilter
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.ui.adapter.ExamAdapter
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.subscribe
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorState
import javax.inject.Inject

@AndroidEntryPoint
@WithFragmentBindings
class ExamListFragment : Fragment(R.layout.fragment_exam_list), ExamListViewModel.EventListener {

	private val viewBinding: FragmentExamListBinding by viewBinding()
	private val viewModel: ExamListViewModel by viewModels()

	@Inject
	lateinit var navigationProvider: ExamListNavigationProvider

	private var adapter: ExamAdapter? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewBinding.setupBaseInsets()

		initSwipeRefresh()
		initAdapter()
		initListeners()
		initObservers()
	}

	private fun initSwipeRefresh() {
		val swipeBackgroundColor = colorFromAttr(R.attr.colorGreyVariant)
		val swipeIndicatorColor = colorFromAttr(R.attr.colorSwipeIndicatorTint)

		viewBinding.swipeRefresh.setProgressBackgroundColorSchemeColor(swipeBackgroundColor)
		viewBinding.swipeRefresh.setColorSchemeColors(swipeIndicatorColor)
	}

	private fun initAdapter() {
		adapter = ExamAdapter(viewModel::selectExam)
		viewBinding.examList.adapter = adapter
	}

	private fun initListeners() {
		viewBinding.swipeRefresh.setOnRefreshListener {
			viewModel.refresh()
		}
		viewBinding.toolbar.menu.findItem(R.id.item_logout).setOnMenuItemClickListener {
			viewModel.logout()
			true
		}
		viewBinding.filter.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

			override fun onTabSelected(tab: TabLayout.Tab) {
				when (tab.position) {
					0    -> viewModel.selectFilter(ExamFilter.ACTIVE)
					1    -> viewModel.selectFilter(ExamFilter.INACTIVE)
					else -> throw IllegalStateException("Incorrect exam filter tab position ${tab.position}")
				}
			}

			override fun onTabUnselected(tab: TabLayout.Tab) = Unit

			override fun onTabReselected(tab: TabLayout.Tab) = Unit
		})
	}

	private fun initObservers() {
		viewModel.state.subscribe(viewLifecycleOwner, ::renderState)
		viewModel.eventDispatcher.bind(viewLifecycleOwner, this)
		viewModel.swipeRefreshEvent.subscribe(viewLifecycleOwner, viewBinding.swipeRefresh::setRefreshing)
	}

	private fun renderState(state: ExamListState) {
		when (state) {
			ExamListState.Initial,
			ExamListState.Loading    -> renderLoadingState()
			ExamListState.NoContent  -> renderNoContentState()
			is ExamListState.Content -> renderContentState(state)
			is ExamListState.Error   -> renderErrorState(state.errorState)
		}
	}

	private fun renderLoadingState() {
		viewBinding.progressBar.isVisible = true
		viewBinding.examList.isVisible = false
		viewBinding.hintExamEmpty.isVisible = false
		viewBinding.filter.isVisible = true
		viewBinding.swipeRefresh.isEnabled = false
		viewBinding.errorView.isVisible = false
	}

	private fun renderNoContentState() {
		viewBinding.progressBar.isVisible = false
		viewBinding.examList.isVisible = false
		viewBinding.hintExamEmpty.isVisible = true
		viewBinding.filter.isVisible = true
		viewBinding.swipeRefresh.isEnabled = true
		viewBinding.errorView.isVisible = false
	}

	private fun renderContentState(state: ExamListState.Content) {
		adapter?.items = state.exams

		viewBinding.progressBar.isVisible = false
		viewBinding.examList.isVisible = true
		viewBinding.hintExamEmpty.isVisible = false
		viewBinding.filter.isVisible = true
		viewBinding.swipeRefresh.isEnabled = true
		viewBinding.errorView.isVisible = false

		val tabPosition = when (state.filter) {
			ExamFilter.ACTIVE   -> 0
			ExamFilter.INACTIVE -> 1
		}
		viewBinding.filter.getTabAt(tabPosition)?.select()
	}

	private fun renderErrorState(errorState: ErrorState) {
		viewBinding.progressBar.isVisible = false
		viewBinding.examList.isVisible = false
		viewBinding.hintExamEmpty.isVisible = false
		viewBinding.swipeRefresh.isEnabled = false
		viewBinding.filter.isVisible = false

		viewBinding.errorView.errorState = errorState
		viewBinding.errorView.errorButtonListener = viewModel::refresh
		viewBinding.errorView.isVisible = true
	}

	override fun navigateToSignIn() {
		navigate(navigationProvider.toSignIn())
	}

	override fun navigateToTicket(args: Bundle) {
		navigate(navigationProvider.toTicket(args))
	}

	override fun onDestroyView() {
		viewBinding.examList.adapter = null
		adapter = null
		super.onDestroyView()
	}
}

