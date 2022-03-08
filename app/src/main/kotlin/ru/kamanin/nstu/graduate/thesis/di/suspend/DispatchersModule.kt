package ru.kamanin.nstu.graduate.thesis.di.suspend

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

	@Provides
	@Singleton
	@ioDispatcher
	fun provideIODispatcher(): CoroutineDispatcher =
		Dispatchers.IO
}