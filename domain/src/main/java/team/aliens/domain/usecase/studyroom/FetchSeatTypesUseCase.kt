package team.aliens.domain.usecase.studyroom

import team.aliens.domain._model.studyroom.FetchSeatTypesInput
import team.aliens.domain._model.studyroom.FetchSeatTypesOutput
import team.aliens.domain._repository.StudyRoomRepository
import javax.inject.Inject

class FetchSeatTypesUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        fetchSeatTypesInput: FetchSeatTypesInput,
    ): FetchSeatTypesOutput {
        return studyRoomRepository.fetchSeatTypes(
            input = fetchSeatTypesInput,
        )
    }
}
