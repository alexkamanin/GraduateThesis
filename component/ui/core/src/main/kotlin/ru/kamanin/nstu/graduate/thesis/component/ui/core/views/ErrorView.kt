package ru.kamanin.nstu.graduate.thesis.component.ui.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import ru.kamanin.nstu.graduate.thesis.component.ui.core.R
import ru.kamanin.nstu.graduate.thesis.component.ui.resources.R.drawable.*
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorState

class ErrorView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

	private val titleView: TextView
	private val descriptionView: TextView
	private val imageView: ImageView
	private val errorButton: MaterialButton

	var errorState: ErrorState = ErrorState.Unknown
		set(value) {
			val (icon, title, description) = when (value) {
				ErrorState.LostConnection     -> Triple(
					ic_error_connection,
					R.string.error_title_lost_connection,
					R.string.error_description_lost_connection
				)
				ErrorState.ServiceUnavailable -> Triple(
					ic_error_server,
					R.string.error_title_service_unavailable,
					R.string.error_description_service_unavailable
				)
				else                          -> Triple(
					ic_error_unknown,
					R.string.error_title_unknown,
					R.string.error_description_unknown
				)
			}

			titleView.text = context.getString(title)
			descriptionView.text = context.getString(description)
			imageView.setImageDrawable(ContextCompat.getDrawable(context, icon))

			field = value
		}

	var errorButtonListener: (() -> Unit)? = null

	init {
		val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val view = inflater.inflate(R.layout.view_error, this, true)

		titleView = view.findViewById(R.id.errorTitle)
		descriptionView = view.findViewById(R.id.errorDescription)
		imageView = view.findViewById(R.id.errorIcon)
		errorButton = view.findViewById(R.id.errorButton)

		errorButton.text = context.getString(R.string.error_button_repeat)
		errorButton.setOnClickListener { errorButtonListener?.invoke() }
	}
}