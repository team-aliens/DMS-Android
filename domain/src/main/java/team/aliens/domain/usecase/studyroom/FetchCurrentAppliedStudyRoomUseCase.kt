package team.aliens.domain.usecase.studyroom

import team.aliens.domain._model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.domain._repository.StudyRoomRepository
import javax.inject.Inject

class FetchCurrentAppliedStudyRoomUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): FetchCurrentAppliedStudyRoomOutput {
        return studyRoomRepository.fetchCurrentAppliedStudyRoom()
    }
}
