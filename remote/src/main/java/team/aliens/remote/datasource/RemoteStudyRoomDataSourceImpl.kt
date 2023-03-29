package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteStudyRoomDataSource
import team.aliens.domain._model.studyroom.*
import java.util.*

class RemoteStudyRoomDataSourceImpl : RemoteStudyRoomDataSource {

    override suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeOutput {
        TODO("Not yet implemented")
    }

    override suspend fun applySeat(
        seatId: UUID,
        timeSlot: UUID,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun cancelSeat(
        timeSlot: UUID,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchStudyRooms(
        timeSlot: UUID,
    ): FetchStudyRoomsOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeSlot: UUID,
    ): FetchStudyRoomDetailsOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCurrentAppliedStudyRoom(): FetchCurrentAppliedStudyRoomOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSeatTypes(): FetchSeatTypesOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesOutput {
        TODO("Not yet implemented")
    }
}
