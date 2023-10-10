package team.aliens.dms_android.domain.usecase.studyroom

import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.dms_android.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchStudyRoomApplicationTimeUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): FetchStudyRoomApplicationTimeOutput {
        return studyRoomRepository.fetchStudyRoomApplicationTime()
    }
}
