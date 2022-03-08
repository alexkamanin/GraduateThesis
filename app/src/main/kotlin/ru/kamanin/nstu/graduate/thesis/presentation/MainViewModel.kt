package ru.kamanin.nstu.graduate.thesis.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.LiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.MutableLiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.asLiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.invoke
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.GetSessionUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val getSessionUseCase: GetSessionUseCase
) : ViewModel() {

	private val _destination = MutableLiveState<Int>()
	val destination: LiveState<Int> get() = _destination.asLiveState()

	init {
		viewModelScope.launch(Dispatchers.Main) {
			runCatching {
				getSessionUseCase()
			}.onSuccess {
				_destination(R.id.main_graph)
			}.onFailure {
				_destination(R.id.sign_graph)
			}
		}
	}
}