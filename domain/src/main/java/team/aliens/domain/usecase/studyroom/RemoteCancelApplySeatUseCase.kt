package team.aliens.domain.usecase.studyroom

import team.aliens.domain.param.CancelStudyRoomParam
import java.util.UUID
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCancelApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<CancelStudyRoomParam, Unit>() {
    override suspend fun execute(cancelStudyRoomParam: CancelStudyRoomParam) {
        studyRoomRepository.cancelApplySeat(
            cancelStudyRoomParam = cancelStudyRoomParam,
        )
    }
}