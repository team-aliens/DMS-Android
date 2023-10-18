package team.aliens.dms.android.domain._legacy.usecase.studyroom

import team.aliens.dms.android.domain.model.studyroom.CurrentAppliedStudyRoom
import team.aliens.dms.android.domain.model.studyroom.toModel
import team.aliens.dms.android.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchCurrentAppliedStudyRoomUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): CurrentAppliedStudyRoom {
        return studyRoomRepository.fetchCurrentAppliedStudyRoom().toModel()
    }
}
