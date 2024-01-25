package team.aliens.dms.android.data.studyroom.mapper

import team.aliens.dms.android.data.studyroom.model.AppliedStudyRoom
import team.aliens.dms.android.data.studyroom.model.AvailableStudyRoomTime
import team.aliens.dms.android.data.studyroom.model.StudyRoom
import team.aliens.dms.android.data.studyroom.model.StudyRoomApplicationTime
import team.aliens.dms.android.network.studyroom.model.FetchAppliedStudyRoomResponse
import team.aliens.dms.android.network.studyroom.model.FetchAvailableStudyRoomTimesResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomApplicationTimeResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomsResponse
import team.aliens.dms.android.shared.model.Sex

fun FetchStudyRoomApplicationTimeResponse.toModel(): StudyRoomApplicationTime =
    StudyRoomApplicationTime(
        startAt = this.startAt,
        endAt = this.endAt,
    )

fun FetchStudyRoomsResponse.toModel(): List<StudyRoom> =
    this.studyRoomResponses.map(FetchStudyRoomsResponse.StudyRoomResponse::toModel)

fun FetchStudyRoomsResponse.StudyRoomResponse.toModel(): StudyRoom = StudyRoom(
    id = this.id,
    floor = this.floor,
    name = this.name,
    availableGrade = this.availableGrade,
    availableSex = Sex.valueOf(this.availableSex),
    inUseHeadcount = this.inUseHeadcount,
    totalAvailableSeat = this.totalAvailableSeat,
    isMine = this.isMine,
)

fun FetchAppliedStudyRoomResponse.toModel(): AppliedStudyRoom = AppliedStudyRoom(
    floor = this.floor,
    name = this.name,
)

fun FetchAvailableStudyRoomTimesResponse.toModel(): List<AvailableStudyRoomTime> =
    this.availableStudyRoomTimes.map(FetchAvailableStudyRoomTimesResponse.AvailableStudyRoomTimeResponse::toModel)

fun FetchAvailableStudyRoomTimesResponse.AvailableStudyRoomTimeResponse.toModel(): AvailableStudyRoomTime =
    AvailableStudyRoomTime(
        id = this.id,
        startTime = this.startTime,
        endTime = this.endTime,
    )
