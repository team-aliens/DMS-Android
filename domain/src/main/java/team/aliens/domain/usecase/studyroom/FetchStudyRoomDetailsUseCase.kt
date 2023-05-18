package team.aliens.domain.usecase.studyroom

import team.aliens.domain.model.studyroom.FetchStudyRoomDetailsInput
import team.aliens.domain.model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchStudyRoomDetailsUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        fetchStudyRoomDetailsInput: FetchStudyRoomDetailsInput,
    ): FetchStudyRoomDetailsOutput {
        return studyRoomRepository.fetchStudyRoomDetails(
            input = fetchStudyRoomDetailsInput,
        )
    }
}
