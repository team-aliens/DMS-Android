package team.aliens.domain.usecase.studyroom

import team.aliens.domain._repository.StudyRoomRepository
import java.util.UUID
import javax.inject.Inject

class ApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        seatId: UUID,
        timeSlot: UUID,
    ) {
        studyRoomRepository.applySeat(
            seatId = seatId,
            timeSlot = timeSlot,
        )
    }
}
