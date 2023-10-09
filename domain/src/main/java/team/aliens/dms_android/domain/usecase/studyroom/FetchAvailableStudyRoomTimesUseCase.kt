package team.aliens.dms_android.domain.usecase.studyroom

import team.aliens.dms_android.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.dms_android.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchAvailableStudyRoomTimesUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): FetchAvailableStudyRoomTimesOutput {
        return studyRoomRepository.fetchAvailableStudyRoomTimes()
    }
}
