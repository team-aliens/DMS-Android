package team.aliens.domain.usecase.studyroom

import team.aliens.domain._model.studyroom.CancelSeatInput
import team.aliens.domain._repository.StudyRoomRepository
import javax.inject.Inject

class CancelSeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        cancelSeatInput: CancelSeatInput,
    ) {
        studyRoomRepository.cancelSeat(
            input = cancelSeatInput,
        )
    }
}
