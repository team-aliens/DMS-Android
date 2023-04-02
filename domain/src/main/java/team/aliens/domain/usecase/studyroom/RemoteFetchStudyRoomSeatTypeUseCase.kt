package team.aliens.domain.usecase.studyroom

import java.util.UUID
import team.aliens.domain.entity.studyroom.SeatTypeEntity
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchStudyRoomSeatTypeUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<UUID, SeatTypeEntity>() {
    override suspend fun execute(studyRoomId: UUID): SeatTypeEntity =
        studyRoomRepository.fetchStudyRoomType(
            studyRoomId = studyRoomId,
        )
}