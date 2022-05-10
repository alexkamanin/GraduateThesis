package ru.kamanin.nstu.graduate.thesis.di.clipdata

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.shared.clipdata.data.repository.ClipDataRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.clipdata.di.CopiedText
import ru.kamanin.nstu.graduate.thesis.shared.clipdata.domain.repository.ClipDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ClipDataModule {

	companion object {

		@Provides
		@Singleton
		@CopiedText
		fun provideClipDataCopiedText(@ApplicationContext context: Context): String =
			context.getString(R.string.clip_data_copied_title)
	}

	@Binds
	@Singleton
	fun bindClipDataRepository(impl: ClipDataRepositoryImpl): ClipDataRepository
}