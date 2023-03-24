package team.aliens.domain.usecase.studyroom

import java.util.UUID
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCancelApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<UUID?, Unit>() {
    override suspend fun execute(data: UUID?) {
        studyRoomRepository.cancelApplySeat(
            timeSlot = data,
        )
    }
}