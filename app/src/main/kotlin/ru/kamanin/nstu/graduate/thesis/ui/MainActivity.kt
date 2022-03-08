package ru.kamanin.nstu.graduate.thesis.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.subscribe
import ru.kamanin.nstu.graduate.thesis.presentation.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private val viewModel: MainViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		setDecorFits()
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		viewModel.destination.subscribe(lifecycleScope, ::setupStartDestination)
	}

	private fun setDecorFits() {
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}

	private fun setupStartDestination(graphId: Int) {
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
		val navController = navHostFragment.navController
		val navInflater = navController.navInflater
		val graph = navInflater.inflate(R.navigation.root_graph)

		graph.setStartDestination(graphId)
		navController.graph = graph
	}
}