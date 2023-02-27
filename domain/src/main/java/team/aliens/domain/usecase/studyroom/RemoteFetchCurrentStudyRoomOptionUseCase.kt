package team.aliens.domain.usecase.studyroom

import team.aliens.domain.entity.studyroom.CurrentStudyRoomOptionEntity
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchCurrentStudyRoomOptionUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<Unit, CurrentStudyRoomOptionEntity>() {
    override suspend fun execute(data: Unit): CurrentStudyRoomOptionEntity =
        studyRoomRepository.fetchCurrentStudyRoomOption()

}