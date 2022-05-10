package ru.kamanin.nstu.graduate.thesis.component.ui.core.snackbar

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.kamanin.nstu.graduate.thesis.component.ui.core.colors.colorFromAttr
import ru.kamanin.nstu.graduate.thesis.component.ui.resources.R.attr.*
import ru.kamanin.nstu.graduate.thesis.component.ui.resources.R.color.*

fun Fragment.showInfoSnackbar(message: String, above: Int? = null) {
	val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)

	above?.let(snackbar::setAnchorView)

	val backgroundColor = colorFromAttr(colorIconTint)
	val textColor = colorFromAttr(colorGreyVariant)

	snackbar.setBackgroundTint(backgroundColor)
	snackbar.setTextColor(textColor)
	snackbar.show()
}

fun Fragment.showErrorSnackbar(message: String, above: Int? = null) {
	val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)

	above?.let(snackbar::setAnchorView)

	val backgroundColor = ContextCompat.getColor(requireContext(), red_700)
	val textColor = ContextCompat.getColor(requireContext(), grey_70)

	snackbar.setBackgroundTint(backgroundColor)
	snackbar.setTextColor(textColor)
	snackbar.show()
}