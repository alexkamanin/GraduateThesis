package ru.kamanin.nstu.graduate.thesis.di.time

import ru.kamanin.nstu.graduate.thesis.BuildConfig
import ru.kamanin.nstu.graduate.thesis.utils.time.TimeManager
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

/**
 * Для тестирования в режиме конфигурации на моках устанавливается время
 * 30.04.2022 10:10:00
 */
@Singleton
class TimeManagerImpl @Inject constructor() : TimeManager {

	private companion object {

		const val LIVE = "live"
		const val MOCK = "develop"

		const val MOCK_TIME = 1651288200000
	}

	private val firstUsedTime by lazy { Date().time }

	override val currentTime: Long
		get() =
			when (BuildConfig.FLAVOR) {
				LIVE -> Date().time
				MOCK -> Date(MOCK_TIME).time + abs(Date().time - firstUsedTime)
				else -> throw IllegalStateException("Product flavor ${BuildConfig.FLAVOR} not found")
			}
}