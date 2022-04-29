package ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.bottom

import android.net.Uri
import java.io.Serializable

sealed interface ShareResult : Serializable {

	data class Content(val uri: Uri) : ShareResult, Serializable

	data class Camera(val uri: Uri) : ShareResult, Serializable

	data class Rationale(val permission: String) : ShareResult, Serializable
}