package team.aliens.domain.usecase.studyroom

import com.example.domain.repository.StudyRoomRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteApplySeatUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        studyRoomRepository.applySeat(data)
    }
}