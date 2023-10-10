package team.aliens.dms_android.domain.usecase.studyroom

import team.aliens.dms_android.domain.model.studyroom.ApplySeatInput
import team.aliens.dms_android.domain.repository.StudyRoomRepository
import javax.inject.Inject

class ApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(
        applySeatInput: ApplySeatInput,
    ) {
        studyRoomRepository.applySeat(
            input = applySeatInput,
        )
    }
}
