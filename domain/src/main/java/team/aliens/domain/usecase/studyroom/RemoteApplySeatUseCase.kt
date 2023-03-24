package team.aliens.domain.usecase.studyroom

import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject
import team.aliens.domain.param.ApplyStudyRoomParam

class RemoteApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<ApplyStudyRoomParam, Unit>() {
    override suspend fun execute(data: ApplyStudyRoomParam) {
        studyRoomRepository.applySeat(
            applyStudyRoomParam = data,
        )
    }
}