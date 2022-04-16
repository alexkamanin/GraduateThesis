package ru.kamanin.nstu.graduate.thesis.feature.sign.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.bind
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.subscribe
import ru.kamanin.nstu.graduate.thesis.component.core.error.ErrorState
import ru.kamanin.nstu.graduate.thesis.component.core.fragment.showErrorDialog
import ru.kamanin.nstu.graduate.thesis.component.core.validation.ValidationResult
import ru.kamanin.nstu.graduate.thesis.component.navigation.navigate
import ru.kamanin.nstu.graduate.thesis.feature.sign.R
import ru.kamanin.nstu.graduate.thesis.feature.sign.databinding.FragmentSignBinding
import ru.kamanin.nstu.graduate.thesis.feature.sign.navigation.SignUpNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.sign.presentation.SignUpState
import ru.kamanin.nstu.graduate.thesis.feature.sign.presentation.SignUpViewModel
import javax.inject.Inject

@AndroidEntryPoint
@WithFragmentBindings
class SignUpFragment : Fragment(R.layout.fragment_sign), SignUpViewModel.EventListener {

	private val viewBinding: FragmentSignBinding by viewBinding()
	private val viewModel: SignUpViewModel by viewModels()

	@Inject
	lateinit var navigationProvider: SignUpNavigationProvider

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViews()
		initInsets()
		initListeners()
		initObservers()
	}

	private fun initViews() {
		viewBinding.emailLayout.isVisible = true
		viewBinding.passwordLayout.isVisible = true
		viewBinding.repeatPasswordLayout.isVisible = true
		viewBinding.verificationCodeLayout.isVisible = true
		viewBinding.toolbar.title = getString(R.string.toolbar_sign_up_title)
		viewBinding.signButton.text = getString(R.string.button_sign_up_text)
	}

	private fun initInsets() {
		ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { windowView, windowInsets ->

			val navigationBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
			val statusBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
			val imeInset = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
			val imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime())

			val bottomPadding = if (imeVisible) {
				imeInset.bottom - viewBinding.accountButton.height - viewBinding.accountButton.marginBottom
			} else {
				navigationBarInset.bottom
			}
			windowView.setPadding(0, statusBarInset.top, 0, bottomPadding)
			windowInsets
		}
	}

	private fun initListeners() {
		viewBinding.signButton.setOnClickListener { viewModel.signUp() }
		viewBinding.toolbar.setNavigationOnClickListener { viewModel.goBack() }
	}

	private fun initObservers() {
		viewModel.email.bind(viewLifecycleOwner, viewBinding.emailText)
		viewModel.password.bind(viewLifecycleOwner, viewBinding.passwordText)
		viewModel.repeatPassword.bind(viewLifecycleOwner, viewBinding.repeatPasswordText)
		viewModel.verificationCode.bind(viewLifecycleOwner, viewBinding.verificationCodeText)
		viewModel.state.subscribe(viewLifecycleOwner, ::renderState)
		viewModel.errorEvent.subscribe(viewLifecycleOwner, ::handleError)
		viewModel.eventDispatcher.bind(viewLifecycleOwner, this)
	}

	private fun renderState(state: SignUpState) {
		viewBinding.signButton.isEnabled = state.registrationAvailable
		viewBinding.emailLayout.error = when (state.emailValidationResult) {
			ValidationResult.VALID, ValidationResult.NONE -> null
			ValidationResult.INVALID                      -> getString(R.string.error_validation_email)
		}
		viewBinding.repeatPasswordLayout.error = if (state.passwordMatch) {
			null
		} else {
			getString(R.string.error_validation_password_match)
		}
		if (state.registrationProcessAvailable) {
			disableFields()
		} else {
			enableFields()
		}
	}

	private fun disableFields() {
		viewBinding.emailText.isEnabled = false
		viewBinding.emailLayout.isEnabled = false
		viewBinding.passwordText.isEnabled = false
		viewBinding.passwordLayout.isEnabled = false
		viewBinding.repeatPasswordText.isEnabled = false
		viewBinding.repeatPasswordLayout.isEnabled = false
		viewBinding.verificationCodeText.isEnabled = false
		viewBinding.verificationCodeLayout.isEnabled = false
		viewBinding.signProcessIndicator.isVisible = true
		viewBinding.signButton.text = null
	}

	private fun enableFields() {
		viewBinding.emailText.isEnabled = true
		viewBinding.emailLayout.isEnabled = true
		viewBinding.passwordText.isEnabled = true
		viewBinding.passwordLayout.isEnabled = true
		viewBinding.repeatPasswordText.isEnabled = true
		viewBinding.repeatPasswordLayout.isEnabled = true
		viewBinding.verificationCodeText.isEnabled = true
		viewBinding.verificationCodeLayout.isEnabled = true
		viewBinding.signProcessIndicator.isVisible = false
		viewBinding.signButton.text = getString(R.string.button_sign_up_text)
	}

	private fun handleError(errorState: ErrorState) {
		showErrorDialog(errorState)
	}

	override fun navigateBack() {
		findNavController().popBackStack()
	}

	override fun navigateToExamsList() {
		navigate(navigationProvider.toExamList)
	}
}