package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteStudyRoomDataSource
import team.aliens.domain._model.studyroom.ApplySeatInput
import team.aliens.domain._model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain._model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.domain._model.studyroom.FetchSeatTypesOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomsOutput
import team.aliens.remote.model.studyroom.toDomain
import team.aliens.remote.service.StudyRoomService
import team.aliens.remote.util.sendHttpRequest
import java.util.UUID
import javax.inject.Inject

class RemoteStudyRoomDataSourceImpl @Inject constructor(
    private val studyRoomService: StudyRoomService,
) : RemoteStudyRoomDataSource {

    override suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeOutput {
        return sendHttpRequest {
            studyRoomService.fetchStudyRoomApplicationTime()
        }.toDomain()
    }

    override suspend fun applySeat(
        input: ApplySeatInput,
    ) {
        return sendHttpRequest {
            studyRoomService.applySeat(
                seatId = input.seatId,
                timeSlot = input.timeSlot,
            )
        }
    }

    override suspend fun cancelSeat(
        seatId: UUID,
        timeSlot: UUID,
    ) {
        return sendHttpRequest {
            studyRoomService.cancelSeat(
                seatId = seatId,
                timeSlot = timeSlot,
            )
        }
    }

    override suspend fun fetchStudyRooms(
        timeSlot: UUID,
    ): FetchStudyRoomsOutput {
        return sendHttpRequest {
            studyRoomService.fetchStudyRooms(
                timeSlot = timeSlot,
            )
        }.toDomain()
    }

    override suspend fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeSlot: UUID,
    ): FetchStudyRoomDetailsOutput {
        return sendHttpRequest {
            studyRoomService.fetchStudyRoomDetails(
                studyRoomId = studyRoomId,
                timeSlot = timeSlot,
            )
        }.toDomain()
    }

    override suspend fun fetchCurrentAppliedStudyRoom(): FetchCurrentAppliedStudyRoomOutput {
        return sendHttpRequest {
            studyRoomService.fetchCurrentAppliedStudyRoom()
        }.toDomain()
    }

    override suspend fun fetchSeatTypes(
        studyRoomId: UUID,
    ): FetchSeatTypesOutput {
        return sendHttpRequest {
            studyRoomService.fetchSeatTypes(
                studyRoomId = studyRoomId,
            )
        }.toDomain()
    }

    override suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesOutput {
        return sendHttpRequest {
            studyRoomService.fetchAvailableStudyRoomTimes()
        }.toDomain()
    }
}
