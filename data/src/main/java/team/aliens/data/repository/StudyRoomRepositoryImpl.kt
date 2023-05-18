package team.aliens.data.repository

import team.aliens.data.datasource.remote.RemoteStudyRoomDataSource
import team.aliens.domain._model.studyroom.ApplySeatInput
import team.aliens.domain._model.studyroom.CancelSeatInput
import team.aliens.domain._model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain._model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.domain._model.studyroom.FetchSeatTypesInput
import team.aliens.domain._model.studyroom.FetchSeatTypesOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsInput
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomsInput
import team.aliens.domain._model.studyroom.FetchStudyRoomsOutput
import team.aliens.domain._repository.StudyRoomRepository
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
