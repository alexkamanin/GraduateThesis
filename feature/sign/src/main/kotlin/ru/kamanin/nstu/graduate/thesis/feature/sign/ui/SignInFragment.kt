package ru.kamanin.nstu.graduate.thesis.feature.sign.ui

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.bind
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.subscribe
import ru.kamanin.nstu.graduate.thesis.component.core.error.ErrorState
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.showErrorDialog
import ru.kamanin.nstu.graduate.thesis.component.core.validation.ValidationResult
import ru.kamanin.nstu.graduate.thesis.component.navigation.navigate
import ru.kamanin.nstu.graduate.thesis.feature.sign.R
import ru.kamanin.nstu.graduate.thesis.feature.sign.databinding.FragmentSignBinding
import ru.kamanin.nstu.graduate.thesis.feature.sign.navigation.SignInNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.sign.presentation.SignInState
import ru.kamanin.nstu.graduate.thesis.feature.sign.presentation.SignInViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign), SignInViewModel.EventListener {

	private val viewBinding: FragmentSignBinding by viewBinding()
	private val viewModel: SignInViewModel by viewModels()

	@Inject
	lateinit var navigationProvider: SignInNavigationProvider

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViews()
		initInsets()
		initListeners()
		initObservers()
	}

	private fun initViews() {
		viewBinding.toolbar.navigationIcon = null
		viewBinding.passwordText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
		viewBinding.passwordLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
		viewBinding.emailLayout.isVisible = true
		viewBinding.passwordLayout.isVisible = true
		viewBinding.accountButton.isVisible = true
		viewBinding.toolbar.title = getString(R.string.toolbar_sign_in_title)
		viewBinding.signButton.text = getString(R.string.button_sign_in_text)
	}

	private fun initInsets() {
		ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { windowView, windowInsets ->

			val navigationBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
			val statusBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
			val imeInset = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
			val imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime())

			val bottomPadding =
				if (imeVisible) {
					imeInset.bottom - viewBinding.accountButton.height - viewBinding.accountButton.marginBottom
				} else {
					navigationBarInset.bottom
				}
			windowView.setPadding(0, statusBarInset.top, 0, bottomPadding)
			windowInsets
		}
	}

	private fun initListeners() {
		viewBinding.signButton.setOnClickListener { viewModel.signIn() }
		viewBinding.accountButton.setOnClickListener { viewModel.goToSignUp() }
	}

	private fun initObservers() {
		viewModel.email.bind(lifecycleScope, viewBinding.emailText)
		viewModel.password.bind(lifecycleScope, viewBinding.passwordText)
		viewModel.eventDispatcher.bind(viewLifecycleOwner, this)
		viewModel.errorEvent.subscribe(viewLifecycleOwner, ::handleError)
		viewModel.state.subscribe(viewLifecycleOwner, ::renderState)
	}

	private fun renderState(state: SignInState) {
		viewBinding.signButton.isEnabled = state.signAvailable
		viewBinding.accountButton.isEnabled = state.registrationAvailable
		viewBinding.emailLayout.error = when (state.emailValidationResult) {
			ValidationResult.VALID, ValidationResult.NONE -> null
			ValidationResult.INVALID                      -> getString(R.string.error_validation_email)
		}
		if (state.signProcessAvailable) {
			viewBinding.emailText.isEnabled = false
			viewBinding.emailLayout.isEnabled = false
			viewBinding.passwordText.isEnabled = false
			viewBinding.passwordLayout.isEnabled = false
			viewBinding.signProcessIndicator.isVisible = true
			viewBinding.signButton.text = null

		} else {
			viewBinding.emailText.isEnabled = true
			viewBinding.emailLayout.isEnabled = true
			viewBinding.passwordText.isEnabled = true
			viewBinding.passwordLayout.isEnabled = true
			viewBinding.signProcessIndicator.isVisible = false
			viewBinding.signButton.text = getString(R.string.button_sign_in_text)
		}
	}

	private fun handleError(errorState: ErrorState) {
		showErrorDialog(errorState)
	}

	override fun navigateToSignUp() {
		navigate(navigationProvider.toSignUp)
	}

	override fun navigateToExamsList() {
		navigate(navigationProvider.toExamList)
	}
}