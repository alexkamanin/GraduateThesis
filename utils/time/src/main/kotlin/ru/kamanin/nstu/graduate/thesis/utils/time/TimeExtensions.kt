package ru.kamanin.nstu.graduate.thesis.utils.time

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ConstantLocale")
private val locale = Locale.getDefault()

private const val dateFormat = "dd.MM.yyyy HH:mm"
private const val timeFormat = "HH:mm"

private val sdf = SimpleDateFormat(dateFormat, locale)
private val tdf = SimpleDateFormat(timeFormat, locale)

val Long.dateStringFormat: String
	get() = Date(this).let(sdf::format)

val Long.timeStringFormat: String
	get() = Date(this).let(tdf::format)