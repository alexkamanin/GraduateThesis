package ru.kamanin.nstu.graduate.thesis.utils.error

sealed interface ErrorState {

	object LostConnection : ErrorState

	object ServiceUnavailable : ErrorState

	object NotAuthorized : ErrorState

	object BadParam : ErrorState

	object NotFound : ErrorState

	object Unknown : ErrorState
}