package team.aliens.domain.usecase.studyroom

import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsInput
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain._repository.StudyRoomRepository
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
