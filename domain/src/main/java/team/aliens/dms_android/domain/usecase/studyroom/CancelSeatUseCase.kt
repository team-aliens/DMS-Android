package team.aliens.dms_android.domain.usecase.studyroom

import team.aliens.domain.model.studyroom.CancelSeatInput
import team.aliens.domain.repository.StudyRoomRepository
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
