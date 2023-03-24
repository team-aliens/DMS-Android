package team.aliens.domain.usecase.studyroom

import java.util.UUID
import javax.inject.Inject
import team.aliens.domain.entity.studyroom.StudyRoomListEntity
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase

class RemoteFetchStudyRoomListUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<UUID?, StudyRoomListEntity>() {
    override suspend fun execute(data: UUID?): StudyRoomListEntity =
        studyRoomRepository.fetchStudyRoomList(
            timeSlot = data,
        )
}