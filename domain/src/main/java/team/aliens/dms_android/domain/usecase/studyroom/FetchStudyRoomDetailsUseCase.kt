package team.aliens.dms_android.domain.usecase.studyroom

import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomDetailsInput
import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.dms_android.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchStudyRoomDetailsUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        fetchStudyRoomDetailsInput: FetchStudyRoomDetailsInput,
    ): FetchStudyRoomDetailsOutput {
        return studyRoomRepository.fetchStudyRoomDetails(
            input = fetchStudyRoomDetailsInput,
        )
    }
}
