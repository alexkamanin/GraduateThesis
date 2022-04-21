package ru.kamanin.nstu.graduate.thesis.shared.chat.data.api

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.dto.MessageDto
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message

class ChatPagingSource @AssistedInject constructor(
	private val api: ChatApi,
	@Assisted private val periodId: Long
) : PagingSource<Int, Message>() {

	companion object {

		private const val INITIAL_PAGE_NUMBER = 0
		private const val PAGE_SIZE = 20

		val PAGING_CONFIG = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE)
	}

	@AssistedFactory
	interface Factory {

		fun create(periodId: Long): ChatPagingSource
	}

	override fun getRefreshKey(state: PagingState<Int, Message>): Int? {
		val anchorPosition = state.anchorPosition ?: return null
		val page = state.closestPageToPosition(anchorPosition) ?: return null
		return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
	}

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Message> {

		val pageNumber: Int = params.key ?: INITIAL_PAGE_NUMBER
		val pageSize: Int = params.loadSize

		return try {
			val paging = api.getMessages(
				periodId = periodId,
				pageNumber = pageNumber,
				pageSize = pageSize
			)

			val content = paging.content.map(MessageDto::toEntity)

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
}