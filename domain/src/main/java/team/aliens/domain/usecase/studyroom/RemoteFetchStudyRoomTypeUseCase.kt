package team.aliens.domain.usecase.studyroom

import team.aliens.domain.entity.studyroom.StudyRoomTypeEntity
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchStudyRoomTypeUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, StudyRoomTypeEntity>() {
    override suspend fun execute(data: Unit): StudyRoomTypeEntity =
        studyRoomRepository.fetchStudyRoomType()
}