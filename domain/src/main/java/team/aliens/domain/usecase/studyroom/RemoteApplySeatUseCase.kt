package team.aliens.domain.usecase.studyroom

import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        studyRoomRepository.applySeat(data)
    }
}