package team.aliens.dms.android.data._legacy.repository

import team.aliens.dms.android.data.datasource.remote.RemoteStudyRoomDataSource
import team.aliens.dms.android.domain.model.studyroom.ApplySeatInput
import team.aliens.dms.android.domain.model.studyroom.CancelSeatInput
import team.aliens.dms.android.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.dms.android.domain.model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.dms.android.domain.model.studyroom.FetchSeatTypesInput
import team.aliens.dms.android.domain.model.studyroom.FetchSeatTypesOutput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomDetailsInput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomsInput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomsOutput
import team.aliens.dms.android.domain.repository.StudyRoomRepository
import javax.inject.Inject

class StudyRoomRepositoryImpl @Inject constructor(
    private val remoteStudyRoomDataSource: RemoteStudyRoomDataSource,
) : StudyRoomRepository {

    override suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeOutput {
        return remoteStudyRoomDataSource.fetchStudyRoomApplicationTime()
    }

    override suspend fun applySeat(
        input: ApplySeatInput,
    ) {
        return remoteStudyRoomDataSource.applySeat(
            input = input,
        )
    }

    override suspend fun cancelSeat(
        input: CancelSeatInput,
    ) {
        return remoteStudyRoomDataSource.cancelSeat(
            input = input,
        )
    }

    override suspend fun fetchStudyRooms(
        input: FetchStudyRoomsInput,
    ): FetchStudyRoomsOutput {
        return remoteStudyRoomDataSource.fetchStudyRooms(
            input = input,
        )
    }

    override suspend fun fetchStudyRoomDetails(
        input: FetchStudyRoomDetailsInput,
    ): FetchStudyRoomDetailsOutput {
        return remoteStudyRoomDataSource.fetchStudyRoomDetails(
            input = input,
        )
    }

    override suspend fun fetchCurrentAppliedStudyRoom(): FetchCurrentAppliedStudyRoomOutput {
        return remoteStudyRoomDataSource.fetchCurrentAppliedStudyRoom()
    }

    override suspend fun fetchSeatTypes(
        input: FetchSeatTypesInput,
    ): FetchSeatTypesOutput {
        return remoteStudyRoomDataSource.fetchSeatTypes(
            input = input,
        )
    }

    override suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesOutput {
        return remoteStudyRoomDataSource.fetchAvailableStudyRoomTimes()
    }
}
