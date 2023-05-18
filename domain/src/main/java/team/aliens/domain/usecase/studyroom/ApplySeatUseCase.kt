package team.aliens.domain.usecase.studyroom

import team.aliens.domain.model.studyroom.ApplySeatInput
import team.aliens.domain.repository.StudyRoomRepository
import javax.inject.Inject

class ApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        applySeatInput: ApplySeatInput,
    ) {
        studyRoomRepository.applySeat(
            input = applySeatInput,
        )
    }
}
