package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteStudyRoomDataSource
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
import team.aliens.remote.model.studyroom.toDomain
import team.aliens.remote.apiservice.StudyRoomApiService
import team.aliens.remote.util.sendHttpRequest
import javax.inject.Inject

class RemoteStudyRoomDataSourceImpl @Inject constructor(
    private val studyRoomApiService: StudyRoomApiService,
) : RemoteStudyRoomDataSource {

    override suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeOutput {
        return sendHttpRequest {
            studyRoomApiService.fetchStudyRoomApplicationTime()
        }.toDomain()
    }

    override suspend fun applySeat(
        input: ApplySeatInput,
    ) {
        return sendHttpRequest {
            studyRoomApiService.applySeat(
                seatId = input.seatId,
                timeSlot = input.timeSlot,
            )
        }
    }

    override suspend fun cancelSeat(
        input: CancelSeatInput,
    ) {
        return sendHttpRequest {
            studyRoomApiService.cancelSeat(
                seatId = input.seatId,
                timeSlot = input.timeSlot,
            )
        }
    }

    override suspend fun fetchStudyRooms(
        input: FetchStudyRoomsInput,
    ): FetchStudyRoomsOutput {
        return sendHttpRequest {
            studyRoomApiService.fetchStudyRooms(
                timeSlot = input.timeSlot,
            )
        }.toDomain()
    }

    override suspend fun fetchStudyRoomDetails(
        input: FetchStudyRoomDetailsInput,
    ): FetchStudyRoomDetailsOutput {
        return sendHttpRequest {
            studyRoomApiService.fetchStudyRoomDetails(
                studyRoomId = input.studyRoomId,
                timeSlot = input.timeSlot,
            )
        }.toDomain()
    }

    override suspend fun fetchCurrentAppliedStudyRoom(): FetchCurrentAppliedStudyRoomOutput {
        return sendHttpRequest {
            studyRoomApiService.fetchCurrentAppliedStudyRoom()
        }.toDomain()
    }

    override suspend fun fetchSeatTypes(
        input: FetchSeatTypesInput,
    ): FetchSeatTypesOutput {
        return sendHttpRequest {
            studyRoomApiService.fetchSeatTypes(
                studyRoomId = input.studyRoomId,
            )
        }.toDomain()
    }

    override suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesOutput {
        return sendHttpRequest {
            studyRoomApiService.fetchAvailableStudyRoomTimes()
        }.toDomain()
    }
}
