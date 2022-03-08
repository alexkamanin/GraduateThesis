package ru.kamanin.nstu.graduate.thesis.di.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ru.kamanin.nstu.graduate.thesis.di.navigation.providers.ExamListNavigationProviderImpl
import ru.kamanin.nstu.graduate.thesis.di.navigation.providers.SignInNavigationProviderImpl
import ru.kamanin.nstu.graduate.thesis.di.navigation.providers.SignUpNavigationProviderImpl
import ru.kamanin.nstu.graduate.thesis.di.navigation.providers.TicketNavigationProviderImpl
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.navigation.ExamListNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.navigation.TicketNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.sign.navigation.SignInNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.sign.navigation.SignUpNavigationProvider

@Module
@InstallIn(FragmentComponent::class)
interface NavigationModule {

	@Binds
	fun bindSignInNavigationProvider(impl: SignInNavigationProviderImpl): SignInNavigationProvider

	@Binds
	fun bindSignUpNavigationProvider(impl: SignUpNavigationProviderImpl): SignUpNavigationProvider

	@Binds
	fun bindExamListNavigationProvider(impl: ExamListNavigationProviderImpl): ExamListNavigationProvider

	@Binds
	fun bindTicketNavigationProvider(impl: TicketNavigationProviderImpl): TicketNavigationProvider
}