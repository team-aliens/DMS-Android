package team.aliens.domain.usecase.studyroom

import com.example.domain.entity.studyroom.StudyRoomTypeEntity
import com.example.domain.repository.StudyRoomRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchStudyRoomTypeUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, StudyRoomTypeEntity>() {
    override suspend fun execute(data: Unit): StudyRoomTypeEntity =
        studyRoomRepository.fetchStudyRoomType()
}