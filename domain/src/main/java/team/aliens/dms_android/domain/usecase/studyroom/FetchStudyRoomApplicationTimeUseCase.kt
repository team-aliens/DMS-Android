package team.aliens.dms_android.domain.usecase.studyroom

import team.aliens.domain.model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchStudyRoomApplicationTimeUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): FetchStudyRoomApplicationTimeOutput {
        return studyRoomRepository.fetchStudyRoomApplicationTime()
    }
}
