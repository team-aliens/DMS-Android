package team.aliens.dms.android.domain._legacy.usecase.studyroom

import team.aliens.dms.android.domain.model.studyroom.FetchSeatTypesInput
import team.aliens.dms.android.domain.model.studyroom.FetchSeatTypesOutput
import team.aliens.dms.android.domain.repository.StudyRoomRepository
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
