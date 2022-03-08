package ru.kamanin.nstu.graduate.thesis.component.core.mvvm.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.coroutines.CoroutineContext

class EventDispatcher<T> : LifecycleEventObserver, CoroutineScope {

	override val coroutineContext: CoroutineContext = Dispatchers.Main

	private var eventsListener: T? = null
	private var activeListener: T? = null
	private var eventsList = LinkedList<T.() -> Unit>()

	override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
		when (event) {
			Lifecycle.Event.ON_RESUME  -> connectListener()
			Lifecycle.Event.ON_PAUSE   -> disconnectListener()
			Lifecycle.Event.ON_DESTROY -> clear(source)
			else                       -> Unit
		}
	}

	private fun connectListener() {
		activeListener = eventsListener
		activeListener?.let { listener ->
			eventsList.forEach { event ->
				event(listener)
			}
			eventsList.clear()
		}
	}

	private fun disconnectListener() {
		activeListener = null
	}

	private fun clear(lifecycleOwner: LifecycleOwner) {
		eventsListener = null
		activeListener = null
		lifecycleOwner.lifecycle.removeObserver(this)
	}

	fun dispatchEvent(event: T.() -> Unit) {
		launch {
			withContext(coroutineContext) {
				activeListener?.also(event) ?: eventsList.add(event)
			}
		}
	}

	fun bind(lifecycleOwner: LifecycleOwner, listener: T) {
		eventsListener = listener
		lifecycleOwner.lifecycle.addObserver(this)
	}
}