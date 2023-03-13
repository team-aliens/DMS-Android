package team.aliens.domain.usecase.studyroom

import team.aliens.domain.repository.StudyRoomRepository
import javax.inject.Inject

class RemoteFetchStudyRoomAvailableTimeListUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke() =
        kotlin.runCatching {
            studyRoomRepository.fetchStudyRoomAvailableTimeList()
        }
}