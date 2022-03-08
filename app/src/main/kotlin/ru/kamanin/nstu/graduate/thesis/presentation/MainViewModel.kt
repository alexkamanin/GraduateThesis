package ru.kamanin.nstu.graduate.thesis.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.LiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.MutableLiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.asLiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.invoke
import ru.kamanin.nstu.graduate.thesis.domain.scenario.ExtendSessionScenario
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val extendSessionScenario: ExtendSessionScenario
) : ViewModel() {

	private val _destination = MutableLiveState<Int>()
	val destination: LiveState<Int> get() = _destination.asLiveState()

	init {
		viewModelScope.launch {
			withContext(Dispatchers.Main.immediate) {
				try {
					extendSessionScenario()
					_destination(R.id.main_graph)
				} catch (_: Throwable) {
					_destination(R.id.sign_graph)
				}
			}
		}
	}
}