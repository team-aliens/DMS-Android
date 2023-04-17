package team.aliens.domain.usecase.studyroom

import team.aliens.domain._model.studyroom.FetchStudyRoomsOutput
import team.aliens.domain._repository.StudyRoomRepository
import java.util.UUID
import javax.inject.Inject

class FetchStudyRoomsUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        timeSlot: UUID,
    ): FetchStudyRoomsOutput {
        return studyRoomRepository.fetchStudyRooms(
            timeSlot = timeSlot,
        )
    }
}
