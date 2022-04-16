package ru.kamanin.nstu.graduate.thesis.artefact.domain.entity

data class Artefact(
	val id: Long,
	val size: Long,
	val extension: String,
	val fullName: String
)