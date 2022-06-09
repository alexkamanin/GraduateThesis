package ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.session.data.repository.SessionConfigRepository
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.entity.SessionConfig
import javax.inject.Inject

class ChangeAutoExtendSessionUseCase @Inject constructor(
	private val repository: SessionConfigRepository
) {

	suspend operator fun invoke(auto: Boolean = true) {
		repository.set(SessionConfig(autoExtend = auto))
	}
}