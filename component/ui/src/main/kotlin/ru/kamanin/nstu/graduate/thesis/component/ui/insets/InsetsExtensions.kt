package ru.kamanin.nstu.graduate.thesis.component.ui.insets

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding

fun ViewBinding.setupBaseInsets() {
	ViewCompat.setOnApplyWindowInsetsListener(root) { windowView, windowInsets ->

		val navigationBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
		val statusBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())

		windowView.setPadding(0, statusBarInset.top, 0, navigationBarInset.bottom)
		windowInsets
	}
}

fun ViewBinding.setupKeyboardInsets() {
	ViewCompat.setOnApplyWindowInsetsListener(root) { windowView, windowInsets ->

		val navigationBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
		val statusBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
		val imeInset = windowInsets.getInsets(WindowInsetsCompat.Type.ime())
		val imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime())

		val bottomPadding = if (imeVisible) {
			imeInset.bottom
		} else {
			navigationBarInset.bottom
		}
		windowView.setPadding(0, statusBarInset.top, 0, bottomPadding)
		windowInsets
	}
}