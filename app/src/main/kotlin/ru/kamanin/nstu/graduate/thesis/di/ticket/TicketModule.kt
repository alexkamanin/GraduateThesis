package ru.kamanin.nstu.graduate.thesis.di.ticket

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.repository.TaskRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.repository.TicketRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository.TaskRepository
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository.TicketRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TicketModule {

	@Binds
	@Singleton
	fun bindTicketRepository(impl: TicketRepositoryImpl): TicketRepository

	@Binds
	@Singleton
	fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository
}