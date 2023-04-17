package team.aliens.domain.usecase.studyroom

import team.aliens.domain._repository.StudyRoomRepository
import java.util.UUID
import javax.inject.Inject

class CancelSeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        timeSlot: UUID,
    ) {
        studyRoomRepository.cancelSeat(
            timeSlot = timeSlot,
        )
    }
}
