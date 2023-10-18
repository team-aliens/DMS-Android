package team.aliens.dms.android.domain._legacy.usecase.studyroom

import team.aliens.dms.android.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.dms.android.domain.repository.StudyRoomRepository
import javax.inject.Inject

class FetchAvailableStudyRoomTimesUseCase @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) {
    suspend operator fun invoke(): FetchAvailableStudyRoomTimesOutput {
        return studyRoomRepository.fetchAvailableStudyRoomTimes()
    }
}
