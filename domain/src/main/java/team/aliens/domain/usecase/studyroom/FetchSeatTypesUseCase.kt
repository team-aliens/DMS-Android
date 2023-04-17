package team.aliens.domain.usecase.studyroom

import team.aliens.domain._model.studyroom.FetchSeatTypesOutput
import team.aliens.domain._repository.StudyRoomRepository
import java.util.UUID
import javax.inject.Inject

class FetchSeatTypesUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        studyRoomId: UUID,
    ): FetchSeatTypesOutput {
        return studyRoomRepository.fetchSeatTypes(
            studyRoomId = studyRoomId,
        )
    }
}
