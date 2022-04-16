package ru.kamanin.nstu.graduate.thesis.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.kamanin.nstu.graduate.thesis.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		setDecorFits()
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	private fun setDecorFits() {
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}
}