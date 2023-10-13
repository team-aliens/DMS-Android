package team.aliens.dms.android.domain.usecase.studyroom

import team.aliens.dms.android.domain.model.studyroom.CancelSeatInput
import team.aliens.dms.android.domain.repository.StudyRoomRepository
import javax.inject.Inject

class CancelSeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        cancelSeatInput: CancelSeatInput,
    ) {
        studyRoomRepository.cancelSeat(
            input = cancelSeatInput,
        )
    }
}
