package ru.kamanin.nstu.graduate.thesis.component.ui.core.colors

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment

@ColorInt
fun Context.colorFromAttr(
	@AttrRes attrColor: Int,
	typedValue: TypedValue = TypedValue(),
	resolveRefs: Boolean = true
): Int {
	theme.resolveAttribute(attrColor, typedValue, resolveRefs)
	return typedValue.data
}

@ColorInt
fun Fragment.colorFromAttr(
	@AttrRes attrColor: Int,
	typedValue: TypedValue = TypedValue(),
	resolveRefs: Boolean = true
): Int {
	requireContext().theme.resolveAttribute(attrColor, typedValue, resolveRefs)
	return typedValue.data
}