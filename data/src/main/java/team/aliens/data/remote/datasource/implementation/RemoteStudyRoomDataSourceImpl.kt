package team.aliens.data.remote.datasource.implementation

import java.util.UUID
import javax.inject.Inject
import team.aliens.data.remote.api.StudyRoomApi
import team.aliens.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import team.aliens.data.remote.response.studyroom.StudyRoomAvailableTimeListResponse
import team.aliens.data.util.sendHttpRequest

class RemoteStudyRoomDataSourceImpl @Inject constructor(
    private val studyRoomApi: StudyRoomApi,
) : RemoteStudyRoomDataSource {

    override suspend fun applySeat(
        seatId: String,
        timeSlot: UUID,
    ) {
        sendHttpRequest(
            httpRequest = suspend {
                studyRoomApi.applySeat(
                    seatId = seatId,
                    timeSlot = timeSlot,
                )
            },
        )
    }

    override suspend fun fetchApplySeatTime() =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchApplySeatTime() })

    override suspend fun cancelApplySeat(
        seatId: String,
        timeSlot: UUID,
    ) {
        sendHttpRequest(
            httpRequest = suspend {
                studyRoomApi.cancelApplySeat(
                    seatId = seatId,
                    timeSlot = timeSlot,
                )
            },
        )
    }

    override suspend fun fetchStudyRoomList(
        timeSlot: UUID,
    ) = sendHttpRequest(
        httpRequest = suspend {
            studyRoomApi.fetchStudyRoomList(
                timeSlot = timeSlot,
            )
        },
    )

    override suspend fun fetchStudyRoomType(
        studyRoomId: UUID,
    ) = sendHttpRequest(
        httpRequest = suspend {
            studyRoomApi.fetchStudyRoomType(
                studyRoomId = studyRoomId,
            )
        },
    )

    override suspend fun fetchStudyRoomDetail(
        roomId: String,
        timeSlot: UUID,
    ) = sendHttpRequest(
        httpRequest = suspend {
            studyRoomApi.fetchStudyRoomDetail(
                roomId = roomId,
                timeSlot = timeSlot,
            )
        },
    )

    override suspend fun fetchCurrentStudyRoomOption() =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchCurrentStudyRoomOption() })

    override suspend fun fetchStudyRoomAvailableTimeList(): StudyRoomAvailableTimeListResponse =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchStudyRoomAvailableTimeList() })
}
