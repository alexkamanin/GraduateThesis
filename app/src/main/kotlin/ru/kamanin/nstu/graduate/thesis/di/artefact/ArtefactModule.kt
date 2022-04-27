package ru.kamanin.nstu.graduate.thesis.di.artefact

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.repository.ArtefactRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.repository.FileInfoRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.ArtefactRepository
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.FileInfoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ArtefactModule {

	@Binds
	@Singleton
	fun bindArtefactRepository(impl: ArtefactRepositoryImpl): ArtefactRepository

	@Binds
	@Singleton
	fun bindFileInfoRepository(impl: FileInfoRepositoryImpl): FileInfoRepository
}