package team.aliens.domain.usecase.studyroom

import team.aliens.domain.entity.studyroom.SeatTypeEntity
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchStudyRoomSeatTypeUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, SeatTypeEntity>() {
    override suspend fun execute(data: Unit): SeatTypeEntity =
        studyRoomRepository.fetchStudyRoomType()
}