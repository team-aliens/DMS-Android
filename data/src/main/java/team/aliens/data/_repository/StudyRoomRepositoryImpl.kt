package team.aliens.data._repository

import team.aliens.data._datasource.remote.RemoteStudyRoomDataSource
import team.aliens.domain._model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain._model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.domain._model.studyroom.FetchSeatTypesOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomsOutput
import team.aliens.domain._repository.StudyRoomRepository
import java.util.UUID
import javax.inject.Inject

class StudyRoomRepositoryImpl @Inject constructor(
    private val remoteStudyRoomDataSource: RemoteStudyRoomDataSource,
) : StudyRoomRepository {

    override suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeOutput {
        return remoteStudyRoomDataSource.fetchStudyRoomApplicationTime()
    }

    override suspend fun applySeat(
        seatId: UUID,
        timeSlot: UUID,
    ) {
        return remoteStudyRoomDataSource.applySeat(
            seatId = seatId,
            timeSlot = timeSlot,
        )
    }

    override suspend fun cancelSeat(
        seatId: UUID,
        timeSlot: UUID,
    ) {
        return remoteStudyRoomDataSource.cancelSeat(
            seatId = seatId,
            timeSlot = timeSlot,
        )
    }

    override suspend fun fetchStudyRooms(
        timeSlot: UUID,
    ): FetchStudyRoomsOutput {
        return remoteStudyRoomDataSource.fetchStudyRooms(
            timeSlot = timeSlot,
        )
    }

    override suspend fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeSlot: UUID,
    ): FetchStudyRoomDetailsOutput {
        return remoteStudyRoomDataSource.fetchStudyRoomDetails(
            studyRoomId = studyRoomId,
            timeSlot = timeSlot,
        )
    }

    override suspend fun fetchCurrentAppliedStudyRoom(): FetchCurrentAppliedStudyRoomOutput {
        return remoteStudyRoomDataSource.fetchCurrentAppliedStudyRoom()
    }

    override suspend fun fetchSeatTypes(
        studyRoomId: UUID,
    ): FetchSeatTypesOutput {
        return remoteStudyRoomDataSource.fetchSeatTypes(
            studyRoomId = studyRoomId,
        )
    }

    override suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesOutput {
        return remoteStudyRoomDataSource.fetchAvailableStudyRoomTimes()
    }
}
