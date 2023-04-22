package team.aliens.domain.usecase.studyroom

import team.aliens.domain._model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.domain._repository.StudyRoomRepository
import javax.inject.Inject

class FetchStudyRoomApplicationTimeUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): FetchStudyRoomApplicationTimeOutput {
        return studyRoomRepository.fetchStudyRoomApplicationTime()
    }
}
