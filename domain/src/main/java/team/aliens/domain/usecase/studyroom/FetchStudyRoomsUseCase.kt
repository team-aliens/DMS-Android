package team.aliens.domain.usecase.studyroom

import team.aliens.domain._model.studyroom.FetchStudyRoomsInput
import team.aliens.domain._model.studyroom.FetchStudyRoomsOutput
import team.aliens.domain._repository.StudyRoomRepository
import javax.inject.Inject

class FetchStudyRoomsUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        fetchStudyRoomsInput: FetchStudyRoomsInput,
    ): FetchStudyRoomsOutput {
        return studyRoomRepository.fetchStudyRooms(
            input = fetchStudyRoomsInput,
        )
    }
}
