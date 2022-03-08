package ru.kamanin.nstu.graduate.thesis.component.core.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ru.kamanin.nstu.graduate.thesis.component.core.R
import ru.kamanin.nstu.graduate.thesis.component.core.error.ErrorState

fun Fragment.showErrorDialog(errorState: ErrorState) {
	val (title, description) = when (errorState) {
		ErrorState.LostConnection     -> R.string.error_title_lost_connection to R.string.error_description_lost_connection
		ErrorState.NotAuthorized      -> R.string.error_title_not_authorized to R.string.error_description_not_authorized
		ErrorState.ServiceUnavailable -> R.string.error_title_service_unavailable to R.string.error_description_service_unavailable
		else                          -> R.string.error_title_unknown to R.string.error_description_unknown
	}
	showInformationDialog(title, description)
}

fun Fragment.showInformationDialog(@StringRes titleId: Int, @StringRes descriptionId: Int) {
	AlertDialog.Builder(requireContext())
		.setTitle(titleId)
		.setMessage(descriptionId)
		.setPositiveButton(R.string.error_button_positive_text, null)
		.create()
		.show()
}

fun Fragment.showFailedPermissionDialog(permission: String, cancel: (() -> Unit)? = null, okay: (() -> Unit)? = null) {
	val (titleId, descriptionId) = when (permission) {
		Manifest.permission.CAMERA                -> R.string.permission_camera_title to R.string.permission_camera_description
		Manifest.permission.READ_EXTERNAL_STORAGE -> R.string.permission_content_title to R.string.permission_content_description
		else                                      -> throw Exception("Permission not found")
	}
	AlertDialog.Builder(requireContext())
		.setTitle(titleId)
		.setMessage(descriptionId)
		.setCancelable(false)
		.setNegativeButton(R.string.error_button_negative_text) { _, _ -> cancel?.invoke() }
		.setPositiveButton(R.string.error_button_positive_text) { _, _ -> okay?.invoke() }
		.create()
		.show()
}

fun Fragment.showSettingDialog() {
	val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
		flags += Intent.FLAG_ACTIVITY_NEW_TASK
		data = Uri.fromParts("package", requireActivity().packageName, null)
	}
	startActivity(intent)
}