package team.aliens.domain.usecase.studyroom

import com.example.domain.entity.studyroom.StudyRoomDetailEntity
import com.example.domain.repository.StudyRoomRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchStudyRoomDetailUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<String, StudyRoomDetailEntity>() {
    override suspend fun execute(data: String): StudyRoomDetailEntity =
        studyRoomRepository.fetchStudyRoomDetail(data)
}