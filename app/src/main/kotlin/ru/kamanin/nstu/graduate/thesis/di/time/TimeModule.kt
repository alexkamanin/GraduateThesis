package ru.kamanin.nstu.graduate.thesis.di.time

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kamanin.nstu.graduate.thesis.component.core.time.TimeManager

@Module
@InstallIn(SingletonComponent::class)
interface TimeModule {

	@Binds
	fun bindTimeManager(impl: TimeManagerImpl): TimeManager
}