package ru.kamanin.nstu.graduate.thesis.shared.clipdata.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.clipdata.domain.repository.ClipDataRepository
import javax.inject.Inject

class SetClipDataUseCase @Inject constructor(
	private val repository: ClipDataRepository
) {

	operator fun invoke(text: String) {
		repository.set(text)
	}
}