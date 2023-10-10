package team.aliens.dms_android.network.datasource

import team.aliens.data.datasource.remote.RemoteStudyRoomDataSource
import team.aliens.dms_android.network.apiservice.StudyRoomApiService
import team.aliens.dms_android.network.model.studyroom.toDomain
import team.aliens.dms_android.network.util.sendHttpRequest
import team.aliens.dms_android.domain.model.studyroom.ApplySeatInput
import team.aliens.dms_android.domain.model.studyroom.CancelSeatInput
import team.aliens.dms_android.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.dms_android.domain.model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.dms_android.domain.model.studyroom.FetchSeatTypesInput
import team.aliens.dms_android.domain.model.studyroom.FetchSeatTypesOutput
import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomDetailsInput
import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomsInput
import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomsOutput
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
