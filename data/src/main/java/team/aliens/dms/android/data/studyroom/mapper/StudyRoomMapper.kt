package team.aliens.dms.android.data.studyroom.mapper

import team.aliens.dms.android.data.studyroom.model.AppliedStudyRoom
import team.aliens.dms.android.data.studyroom.model.StudyRoomApplicationTime
import team.aliens.dms.android.network.studyroom.model.FetchAppliedStudyRoomResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomApplicationTimeResponse

fun FetchStudyRoomApplicationTimeResponse.toModel() : StudyRoomApplicationTime = StudyRoomApplicationTime(
    startAt = this.startAt,
    endAt = this.endAt,
)

fun FetchAppliedStudyRoomResponse.toModel(): AppliedStudyRoom = AppliedStudyRoom(
    floor = this.floor,
    name = this.name,
)
