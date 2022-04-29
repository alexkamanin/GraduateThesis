package ru.kamanin.nstu.graduate.thesis.di.artefact

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource.*
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.repository.ArtefactRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.repository.DocumentTreeRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.repository.FileInfoRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.delegate.ArtefactViewModelDelegate
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.delegate.ArtefactViewModelDelegateImpl
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.ArtefactRepository
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.DocumentTreeRepository
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.FileInfoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ArtefactModule {

	@Binds
	@Singleton
	fun bindMediaDataSource(impl: MediaDataSourceImpl): MediaDataSource

	@Binds
	@Singleton
	fun bindDocumentDataSource(impl: DocumentDataSourceImpl): DocumentDataSource

	@Binds
	@Singleton
	fun bindDocumentTreeDataSource(impl: DocumentTreeDataSourceImpl): DocumentTreeDataSource

	@Binds
	@Singleton
	fun bindDocumentTreeRepository(impl: DocumentTreeRepositoryImpl): DocumentTreeRepository

	@Binds
	@Singleton
	fun bindCacheDataSource(impl: CacheDataSourceImpl): CacheDataSource

	@Binds
	@Singleton
	fun bindFileInfoDataSource(impl: FileInfoDataSourceImpl): FileInfoDataSource

	@Binds
	@Singleton
	fun bindFileInfoRepository(impl: FileInfoRepositoryImpl): FileInfoRepository

	@Binds
	@Singleton
	fun bindArtefactDataSource(impl: ArtefactDataSourceImpl): ArtefactDataSource

	@Binds
	@Singleton
	fun bindArtefactRepository(impl: ArtefactRepositoryImpl): ArtefactRepository

	@Binds
	fun bindArtefactViewModelDelegate(impl: ArtefactViewModelDelegateImpl): ArtefactViewModelDelegate
}