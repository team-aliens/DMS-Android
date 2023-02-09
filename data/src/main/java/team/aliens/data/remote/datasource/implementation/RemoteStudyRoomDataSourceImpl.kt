package team.aliens.data.remote.datasource.implementation

import team.aliens.data.remote.api.StudyRoomApi
import team.aliens.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import team.aliens.data.util.sendHttpRequest
import javax.inject.Inject

class RemoteStudyRoomDataSourceImpl @Inject constructor(
    private val studyRoomApi: StudyRoomApi,
) : RemoteStudyRoomDataSource {

    override suspend fun applySeat(data: String) {
        sendHttpRequest(httpRequest = suspend { studyRoomApi.applySeat(data) })
    }

    override suspend fun fetchApplySeatTime() =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchApplySeatTime() })

    override suspend fun cancelApplySeat() {
        sendHttpRequest(httpRequest = suspend { studyRoomApi.cancelApplySeat() })
    }

    override suspend fun fetchStudyRoomList() =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchStudyRoomList() })

    override suspend fun fetchStudyRoomType() =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchStudyRoomType() })

    override suspend fun fetchStudyRoomDetail(roomId: String) =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchStudyRoomDetail(roomId) })
}