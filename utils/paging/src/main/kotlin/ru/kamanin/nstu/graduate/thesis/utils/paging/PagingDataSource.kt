package ru.kamanin.nstu.graduate.thesis.utils.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class PagingDataSource<T : Any> : PagingSource<Int, T>() {

	companion object {

		private const val INITIAL_PAGE_NUMBER = 0
		private const val PAGE_SIZE = 20

		val PAGING_CONFIG = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE)
	}

	override fun getRefreshKey(state: PagingState<Int, T>): Int? {
		val anchorPosition = state.anchorPosition ?: return null
		val page = state.closestPageToPosition(anchorPosition) ?: return null
		return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
	}

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {

		val pageNumber: Int = params.key ?: INITIAL_PAGE_NUMBER
		val pageSize: Int = params.loadSize

		return try {
			val paging = load(pageNumber, pageSize)
			val content = paging.content

			val (messages, prev, next) = when {
				paging.empty -> Triple(emptyList(), null, null)
				paging.first -> Triple(content, null, paging.number + 1)
				paging.last  -> Triple(content, paging.number - 1, null)

				else         -> Triple(content, paging.number - 1, paging.number + 1)
			}

			LoadResult.Page(messages, prev, next)
		} catch (throwable: Throwable) {
			LoadResult.Error(throwable)
		}
	}

	abstract suspend fun load(pageNumber: Int, pageSize: Int): Paging<T>
}