package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity

enum class RatingState {
	EMPTY,
	NOT_ALLOWED,
	ALLOWED,
	ASSIGNED_TO_EXAM,
	WAITING_TO_APPEAR,
	ABSENT,
	PASSING,
	FINISHED,
	RATED
}