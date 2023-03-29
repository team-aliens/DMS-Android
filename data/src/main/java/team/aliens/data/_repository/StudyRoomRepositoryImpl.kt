package team.aliens.data._repository

import team.aliens.domain._model.studyroom.*
import team.aliens.domain._repository.StudyRoomRepository
import java.util.*

class StudyRoomRepositoryImpl(
    // private val remoteStudyRoomDataSource: RemoteStudyRoomDataSource,
) : StudyRoomRepository {

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
