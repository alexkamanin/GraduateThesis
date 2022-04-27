package ru.kamanin.nstu.graduate.thesis.utils.paging

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Paging<T>(
	val content: List<T>,
	val pageable: Pageable,
	val totalPages: Int,            // кол-во всех страниц по данному запросу
	val totalElements: Int,         // кол-во всех элементов по данном запросу
	val number: Int,                // номер страницы
	val size: Int,                  // размер страницы
	val numberOfElements: Int,      // кол-во полученных элементов на странице
	val first: Boolean,             // первая страница
	val last: Boolean,              // последняя страница
	val empty: Boolean
) {

	@JsonClass(generateAdapter = true)
	data class Pageable(
		val pageNumber: Int,
		val pageSize: Int,
		val offset: Int,
	)
}