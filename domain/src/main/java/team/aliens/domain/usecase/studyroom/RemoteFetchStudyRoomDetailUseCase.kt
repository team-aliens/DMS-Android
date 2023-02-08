package team.aliens.domain.usecase.studyroom

import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchStudyRoomDetailUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<String, StudyRoomDetailEntity>() {
    override suspend fun execute(data: String): StudyRoomDetailEntity =
        studyRoomRepository.fetchStudyRoomDetail(data)
}