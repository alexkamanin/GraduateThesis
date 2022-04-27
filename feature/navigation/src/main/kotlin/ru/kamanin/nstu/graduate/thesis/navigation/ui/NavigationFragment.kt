package ru.kamanin.nstu.graduate.thesis.navigation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.kamanin.nstu.graduate.thesis.component.navigation.navigate
import ru.kamanin.nstu.graduate.thesis.component.ui.insets.setupBaseInsets
import ru.kamanin.nstu.graduate.thesis.navigation.R
import ru.kamanin.nstu.graduate.thesis.navigation.databinding.FragmentNavigationBinding
import ru.kamanin.nstu.graduate.thesis.navigation.navigation.NavigationProvider
import ru.kamanin.nstu.graduate.thesis.navigation.presentation.NavigationState
import ru.kamanin.nstu.graduate.thesis.navigation.presentation.NavigationViewModel
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.subscribe
import javax.inject.Inject

@AndroidEntryPoint
class NavigationFragment : Fragment(R.layout.fragment_navigation), NavigationViewModel.EventListener {

	private val viewBinding: FragmentNavigationBinding by viewBinding()
	private val viewModel: NavigationViewModel by viewModels()

	@Inject
	lateinit var navigationProvider: NavigationProvider

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewBinding.setupBaseInsets()

		viewModel.eventDispatcher.bind(viewLifecycleOwner, this)
		viewModel.state.subscribe(viewLifecycleOwner) { state ->

			when (state) {

				NavigationState.Initial,
				NavigationState.Loading  -> {
					viewBinding.progressView.isVisible = true
					viewBinding.errorView.isVisible = false
				}

				is NavigationState.Error -> {
					viewBinding.progressView.isVisible = false
					viewBinding.errorView.errorState = state.errorState
					viewBinding.errorView.errorButtonListener = viewModel::repeat
					viewBinding.errorView.isVisible = true
				}
			}
		}
	}

	override fun navigateToSignIn() {
		navigate(navigationProvider.toSign)
	}

	override fun navigateToExamsList() {
		navigate(navigationProvider.toExamList)
	}
}