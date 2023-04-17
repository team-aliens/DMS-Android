package team.aliens.domain.usecase.studyroom

import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain._repository.StudyRoomRepository
import java.util.UUID
import javax.inject.Inject

class FetchStudyRoomDetailsUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        studyRoomId: UUID,
        timeSlot: UUID,
    ): FetchStudyRoomDetailsOutput {
        return studyRoomRepository.fetchStudyRoomDetails(
            studyRoomId = studyRoomId,
            timeSlot = timeSlot,
        )
    }
}
