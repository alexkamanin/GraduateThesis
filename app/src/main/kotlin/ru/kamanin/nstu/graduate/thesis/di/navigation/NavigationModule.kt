package ru.kamanin.nstu.graduate.thesis.di.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ru.kamanin.nstu.graduate.thesis.di.navigation.providers.*
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.navigation.ExamListNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.navigation.TicketNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.sign.navigation.SignInNavigationProvider
import ru.kamanin.nstu.graduate.thesis.feature.sign.navigation.SignUpNavigationProvider
import ru.kamanin.nstu.graduate.thesis.navigation.navigation.NavigationProvider

@Module
@InstallIn(FragmentComponent::class)
interface NavigationModule {

	@Binds
	fun bindNavigationProvider(impl: NavigationProviderImpl): NavigationProvider

	@Binds
	fun bindSignInNavigationProvider(impl: SignInNavigationProviderImpl): SignInNavigationProvider

	@Binds
	fun bindSignUpNavigationProvider(impl: SignUpNavigationProviderImpl): SignUpNavigationProvider

	@Binds
	fun bindExamListNavigationProvider(impl: ExamListNavigationProviderImpl): ExamListNavigationProvider

	@Binds
	fun bindTicketNavigationProvider(impl: TicketNavigationProviderImpl): TicketNavigationProvider
}