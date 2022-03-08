package ru.kamanin.nstu.graduate.thesis.di.exam

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.repository.ExamRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository.ExamRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ExamModule {

	@Binds
	@Singleton
	fun bindExamRepository(impl: ExamRepositoryImpl): ExamRepository
}