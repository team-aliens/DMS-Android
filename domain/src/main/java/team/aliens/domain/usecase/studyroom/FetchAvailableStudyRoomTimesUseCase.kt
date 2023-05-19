package team.aliens.domain.usecase.studyroom

import team.aliens.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchAvailableStudyRoomTimesUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): FetchAvailableStudyRoomTimesOutput {
        return studyRoomRepository.fetchAvailableStudyRoomTimes()
    }
}
