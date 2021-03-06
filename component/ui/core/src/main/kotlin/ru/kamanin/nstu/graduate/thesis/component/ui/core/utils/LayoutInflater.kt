package ru.kamanin.nstu.graduate.thesis.component.ui.core.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): View =
	LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)