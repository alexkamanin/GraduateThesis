package ru.kamanin.graduate.thesis.shared.notification.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ExamChannelId

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ExamChannelName

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class AnswerChannelId

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class AnswerChannelName

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class MessageChannelId

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class MessageChannelName