package team.aliens.domain.usecase.studyroom

import team.aliens.domain.model.studyroom.FetchSeatTypesInput
import team.aliens.domain.model.studyroom.FetchSeatTypesOutput
import team.aliens.domain.repository.StudyRoomRepository
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
