package team.aliens.dms_android.domain.usecase.studyroom

import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomsInput
import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomsOutput
import team.aliens.dms_android.domain.repository.StudyRoomRepository
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
