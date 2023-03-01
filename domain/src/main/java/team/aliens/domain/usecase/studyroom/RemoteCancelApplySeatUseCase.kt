package team.aliens.domain.usecase.studyroom

import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCancelApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, Unit>() {
    override suspend fun execute(data: Unit) {
        studyRoomRepository.cancelApplySeat()
    }
}