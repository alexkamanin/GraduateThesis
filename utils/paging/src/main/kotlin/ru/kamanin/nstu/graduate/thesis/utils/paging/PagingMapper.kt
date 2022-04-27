package ru.kamanin.nstu.graduate.thesis.utils.paging

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <T : Any, R : Any> Flow<PagingData<T>>.mapPaging(
	crossinline transform: suspend (T) -> R
): Flow<PagingData<R>> =
	this.map { paging -> paging.map { data -> transform(data) } }