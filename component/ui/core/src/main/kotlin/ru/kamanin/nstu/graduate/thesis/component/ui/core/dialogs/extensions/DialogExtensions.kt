package ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.extensions

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.kamanin.nstu.graduate.thesis.component.ui.core.R
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorState

fun Fragment.showErrorDialog(
	errorState: ErrorState,
	okay: (() -> Unit)? = null
) {
	val (title, description) = when (errorState) {
		ErrorState.LostConnection     -> R.string.error_title_lost_connection to R.string.error_description_lost_connection
		ErrorState.NotAuthorized      -> R.string.error_title_not_authorized to R.string.error_description_not_authorized
		ErrorState.ServiceUnavailable -> R.string.error_title_service_unavailable to R.string.error_description_service_unavailable
		else                          -> R.string.error_title_unknown to R.string.error_description_unknown
	}
	showInformationDialog(title, description, okay)
}

fun Fragment.showInformationDialog(
	@StringRes titleId: Int,
	@StringRes descriptionId: Int,
	okay: (() -> Unit)? = null
) {
	MaterialAlertDialogBuilder(requireContext())
		.setTitle(titleId)
		.setCancelable(false)
		.setMessage(descriptionId)
		.setPositiveButton(R.string.error_button_positive_text) { _, _ -> okay?.invoke() }
		.create()
		.show()
}

fun Fragment.showFailedPermissionDialog(permission: String, cancel: (() -> Unit)? = null, okay: (() -> Unit)? = null) {
	val (titleId, descriptionId) = when (permission) {
		Manifest.permission.CAMERA                 -> R.string.permission_camera_title to R.string.permission_camera_description

		Manifest.permission.READ_EXTERNAL_STORAGE,
		Manifest.permission.WRITE_EXTERNAL_STORAGE -> R.string.permission_content_title to R.string.permission_content_description

		else                                       -> throw Exception("Permission not found")
	}
	MaterialAlertDialogBuilder(requireContext())
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