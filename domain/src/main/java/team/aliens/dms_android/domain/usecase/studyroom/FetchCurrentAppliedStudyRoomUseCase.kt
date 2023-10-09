package team.aliens.dms_android.domain.usecase.studyroom

import team.aliens.dms_android.domain.model.studyroom.CurrentAppliedStudyRoom
import team.aliens.dms_android.domain.model.studyroom.toModel
import team.aliens.dms_android.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchCurrentAppliedStudyRoomUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): CurrentAppliedStudyRoom {
        return studyRoomRepository.fetchCurrentAppliedStudyRoom().toModel()
    }
}
