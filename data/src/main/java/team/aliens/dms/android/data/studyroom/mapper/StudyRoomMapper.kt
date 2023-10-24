package team.aliens.dms.android.data.studyroom.mapper

import team.aliens.dms.android.data.studyroom.model.AppliedStudyRoom
import team.aliens.dms.android.network.studyroom.model.FetchAppliedStudyRoomResponse

fun FetchAppliedStudyRoomResponse.toModel(): AppliedStudyRoom = AppliedStudyRoom(
    floor = this.floor,
    name = this.name,
)
