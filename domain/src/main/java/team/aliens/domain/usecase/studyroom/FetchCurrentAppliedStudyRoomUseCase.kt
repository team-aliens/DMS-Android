package team.aliens.domain.usecase.studyroom

import team.aliens.domain.model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchCurrentAppliedStudyRoomUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): FetchCurrentAppliedStudyRoomOutput {
        return studyRoomRepository.fetchCurrentAppliedStudyRoom()
    }
}
