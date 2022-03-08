package ru.kamanin.nstu.graduate.thesis.component.core.fragment.dialog

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kamanin.nstu.graduate.thesis.component.core.R
import ru.kamanin.nstu.graduate.thesis.component.core.databinding.ShareBottomSheetBinding

private const val SHARE_BOTTOM_SHEET_DIALOG_TAG = "SHARE_BOTTOM_SHEET"
private const val SHARE_BOTTOM_SHEET_DIALOG_KEY = "SHARE_BOTTOM_SHEET_DIALOG"
private const val SHARE_BOTTOM_SHEET_DIALOG_RESULT = "RESULT"

fun Fragment.showShareBottomSheetDialog() {
	ShareBottomSheetDialog().show(parentFragmentManager, SHARE_BOTTOM_SHEET_DIALOG_TAG)
}

fun Fragment.setShareBottomSheetResultListener(observer: (ShareResult) -> Unit) {
	setFragmentResultListener(SHARE_BOTTOM_SHEET_DIALOG_KEY) { _, bundle ->
		val result = bundle.getSerializable(SHARE_BOTTOM_SHEET_DIALOG_RESULT) as ShareResult
		observer(result)
	}
}

internal class ShareBottomSheetDialog : BottomSheetDialogFragment() {

	private companion object {

		const val ALL = "*/*"
		const val IMAGE = "image/*"
	}

	private val viewBinding: ShareBottomSheetBinding by viewBinding(ShareBottomSheetBinding::bind)

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.share_bottom_sheet, container, false)
	}

	private val pickContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
		if (uri == null) {
			dismiss()
			return@registerForActivityResult
		}
		parentFragmentManager.setFragmentResult(
			SHARE_BOTTOM_SHEET_DIALOG_KEY,
			bundleOf(SHARE_BOTTOM_SHEET_DIALOG_RESULT to ShareResult.Content(uri))
		)
		dismiss()
	}

	private val pickCamera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
		if (bitmap == null) {
			dismiss()
			return@registerForActivityResult
		}
		parentFragmentManager.setFragmentResult(
			SHARE_BOTTOM_SHEET_DIALOG_KEY,
			bundleOf(SHARE_BOTTOM_SHEET_DIALOG_RESULT to ShareResult.Camera(bitmap))
		)
		dismiss()
	}

	private val cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
		val shouldRationale = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
		when {
			shouldRationale || !granted -> showRationale(Manifest.permission.CAMERA)
			granted                     -> pickCamera.launch()
		}
	}

	private val contentPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
		val shouldRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
		when {
			shouldRationale || !granted -> showRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
			granted                     -> pickContent.launch(IMAGE)
		}
	}

	private fun showRationale(permission: String) {
		parentFragmentManager.setFragmentResult(
			SHARE_BOTTOM_SHEET_DIALOG_KEY,
			bundleOf(SHARE_BOTTOM_SHEET_DIALOG_RESULT to ShareResult.Rationale(permission))
		)
		dismiss()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewBinding.pickCamera.setOnClickListener {
			cameraPermission.launch(Manifest.permission.CAMERA)
		}
		viewBinding.pickGallery.setOnClickListener {
			contentPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
		}
		viewBinding.pickDocument.setOnClickListener {
			pickContent.launch(ALL)
		}
	}
}