package team.aliens.domain.usecase.studyroom

import team.aliens.domain.entity.studyroom.ApplySeatTimeEntity
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, ApplySeatTimeEntity>() {
    override suspend fun execute(data: Unit): ApplySeatTimeEntity =
        studyRoomRepository.fetchApplySeatTime()
}