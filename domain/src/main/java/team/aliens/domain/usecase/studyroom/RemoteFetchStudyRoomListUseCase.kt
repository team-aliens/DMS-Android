package team.aliens.domain.usecase.studyroom

import team.aliens.domain.entity.studyroom.StudyRoomListEntity
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchStudyRoomListUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, StudyRoomListEntity>() {
    override suspend fun execute(data: Unit): StudyRoomListEntity =
        studyRoomRepository.fetchStudyRoomList()
}