package ru.kamanin.nstu.graduate.thesis.component.core.time

import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale.getDefault()
private val format = "dd.MM.yyyy HH:mm"

private val sdf = SimpleDateFormat(format, locale)

val Long.dateStringFormat: String
	get() = Date(this).let(sdf::format)