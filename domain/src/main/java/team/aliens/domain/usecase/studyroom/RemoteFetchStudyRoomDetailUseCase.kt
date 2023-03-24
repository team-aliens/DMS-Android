package team.aliens.domain.usecase.studyroom

import javax.inject.Inject
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity
import team.aliens.domain.param.StudyRoomDetailParam
import team.aliens.domain.repository.StudyRoomRepository
import team.aliens.domain.usecase.UseCase

class RemoteFetchStudyRoomDetailUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : UseCase<StudyRoomDetailParam, StudyRoomDetailEntity>() {
    override suspend fun execute(data: StudyRoomDetailParam): StudyRoomDetailEntity =
        studyRoomRepository.fetchStudyRoomDetail(
            studyRoomDetailParam = data,
        )
}