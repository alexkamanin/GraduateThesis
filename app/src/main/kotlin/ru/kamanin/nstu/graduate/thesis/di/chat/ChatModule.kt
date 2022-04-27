package ru.kamanin.nstu.graduate.thesis.di.chat

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.repository.ChatRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.repository.ChatRepository

@Module
@InstallIn(SingletonComponent::class)
interface ChatModule {

	@Binds
	fun bindChatRepository(impl: ChatRepositoryImpl): ChatRepository
}