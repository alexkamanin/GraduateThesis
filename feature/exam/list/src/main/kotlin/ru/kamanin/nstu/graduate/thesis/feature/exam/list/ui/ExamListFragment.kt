package ru.kamanin.nstu.graduate.thesis.feature.exam.list.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.subscribe
import ru.kamanin.nstu.graduate.thesis.component.navigation.navigate
import ru.kamanin.nstu.graduate.thesis.component.ui.colors.colorFromAttr
import ru.kamanin.nstu.graduate.thesis.component.ui.insets.setupBaseInsets
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.databinding.FragmentExamListBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.navigation.ExamListNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation.ExamListState
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation.ExamListViewModel
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.ui.adapter.ExamAdapter
import javax.inject.Inject

@AndroidEntryPoint
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
	}

	private fun initObservers() {
		viewModel.state.subscribe(lifecycleScope, ::renderState)
		viewModel.eventDispatcher.bind(viewLifecycleOwner, this)
		viewModel.swipeRefreshEvent.subscribe(lifecycleScope, viewBinding.swipeRefresh::setRefreshing)
	}

	private fun renderState(state: ExamListState) {
		when (state) {
			ExamListState.Loading    -> renderLoadingState()
			ExamListState.NoContent  -> renderNoContentState()
			is ExamListState.Content -> renderContentState(state)
		}
	}

	private fun renderLoadingState() {
		viewBinding.progressBar.isVisible = true
		viewBinding.examList.isVisible = false
		viewBinding.hintExamEmpty.isVisible = false
	}

	private fun renderNoContentState() {
		viewBinding.progressBar.isVisible = false
		viewBinding.examList.isVisible = false
		viewBinding.hintExamEmpty.isVisible = true
	}

	private fun renderContentState(state: ExamListState.Content) {
		adapter?.items = state.examItems
		viewBinding.progressBar.isVisible = false
		viewBinding.examList.isVisible = true
		viewBinding.hintExamEmpty.isVisible = false
	}

	override fun navigateToSignIn() {
		navigate(navigationProvider.toSignIn)
	}

	override fun navigateToTicket() {
		navigate(navigationProvider.toTicket)
	}

	override fun onDestroyView() {
		viewBinding.examList.adapter = null
		adapter = null
		super.onDestroyView()
	}
}

