package ru.kamanin.nstu.graduate.thesis.component.ui.core.dialogs.bottom

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
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
import ru.kamanin.nstu.graduate.thesis.component.ui.core.R
import ru.kamanin.nstu.graduate.thesis.component.ui.core.databinding.ShareBottomSheetBinding
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.contracts.TakePhotoContract

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

		const val ALL = "application/*"
	}

	private val viewBinding: ShareBottomSheetBinding by viewBinding(ShareBottomSheetBinding::bind)

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.share_bottom_sheet, container, false)
	}

	private val pickContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
		if (result.resultCode == Activity.RESULT_OK) {
			val uri = result.data?.data
			if (uri == null) {
				dismiss()
				return@registerForActivityResult
			}
			parentFragmentManager.setFragmentResult(
				SHARE_BOTTOM_SHEET_DIALOG_KEY,
				bundleOf(SHARE_BOTTOM_SHEET_DIALOG_RESULT to ShareResult.Content(uri))
			)
			dismiss()
		} else {
			dismiss()
		}
	}

	private val pickCamera = registerForActivityResult(TakePhotoContract()) { uri ->
		if (uri != null) {
			parentFragmentManager.setFragmentResult(
				SHARE_BOTTOM_SHEET_DIALOG_KEY,
				bundleOf(SHARE_BOTTOM_SHEET_DIALOG_RESULT to ShareResult.Camera(uri))
			)
		}
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
			granted                     -> showGallery()
		}
	}

	private fun showGallery() {
		val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
		pickContent.launch(galleryIntent)
	}

	private fun showFileChooser() {
		val documentIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
			addCategory(Intent.CATEGORY_OPENABLE)
			type = ALL
		}

		pickContent.launch(documentIntent)
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
			showFileChooser()
		}
	}
}