package team.aliens.domain.usecase.studyroom

import com.example.domain.entity.studyroom.StudyRoomListEntity
import com.example.domain.repository.StudyRoomRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchStudyRoomListUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, StudyRoomListEntity>() {
    override suspend fun execute(data: Unit): StudyRoomListEntity =
        studyRoomRepository.fetchStudyRoomList()
}