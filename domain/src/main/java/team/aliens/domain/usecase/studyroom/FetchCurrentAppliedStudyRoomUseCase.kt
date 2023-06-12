package team.aliens.domain.usecase.studyroom

import team.aliens.domain.model.studyroom.CurrentAppliedStudyRoom
import team.aliens.domain.model.studyroom.toModel
import team.aliens.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchCurrentAppliedStudyRoomUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): CurrentAppliedStudyRoom {
        return studyRoomRepository.fetchCurrentAppliedStudyRoom().toModel()
    }
}
