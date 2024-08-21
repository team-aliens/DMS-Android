package team.aliens.dms.android.data.studyroom.mapper

import team.aliens.dms.android.data.studyroom.model.AppliedStudyRoom
import team.aliens.dms.android.data.studyroom.model.AvailableStudyRoomTime
import team.aliens.dms.android.data.studyroom.model.StudyRoom
import team.aliens.dms.android.data.studyroom.model.StudyRoomApplicationTime
import team.aliens.dms.android.network.studyroom.model.FetchAppliedStudyRoomResponse
import team.aliens.dms.android.network.studyroom.model.FetchAvailableStudyRoomTimesResponse
import team.aliens.dms.android.network.studyroom.model.FetchSeatTypesResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomApplicationTimeResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomDetailsResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomsResponse
import team.aliens.dms.android.shared.model.Sex

internal fun FetchStudyRoomApplicationTimeResponse.toModel(): StudyRoomApplicationTime =
    StudyRoomApplicationTime(
        startAt = this.startAt,
        endAt = this.endAt,
    )

internal fun FetchStudyRoomsResponse.toModel(): List<StudyRoom> =
    this.studyRoomResponses.map(FetchStudyRoomsResponse.StudyRoomResponse::toModel)

private fun FetchStudyRoomsResponse.StudyRoomResponse.toModel(): StudyRoom = StudyRoom(
    id = this.id,
    floor = this.floor,
    name = this.name,
    availableGrade = this.availableGrade,
    availableSex = Sex.valueOf(this.availableSex),
    inUseHeadcount = this.inUseHeadcount,
    totalAvailableSeat = this.totalAvailableSeat,
    isMine = this.isMine,
)

internal fun FetchStudyRoomDetailsResponse.toModel(): StudyRoom.Details = StudyRoom.Details(
    id = id,
    floor = floor,
    name = name,
    startTime = startTime,
    endTime = endTime,
    totalAvailableSeat = totalAvailableSeat,
    inUseHeadcount = inUseHeadcount,
    availableSex = Sex.valueOf(this.availableSex),
    availableGrade = availableGrade,
    eastDescription = eastDescription,
    westDescription = westDescription,
    southDescription = southDescription,
    northDescription = northDescription,
    totalWidthSize = totalWidthSize,
    totalHeightSize = totalHeightSize,
    seats = seats.toModel(),
)

private fun List<FetchStudyRoomDetailsResponse.SeatResponse>.toModel(): List<StudyRoom.Seat> =
    this.map(FetchStudyRoomDetailsResponse.SeatResponse::toModel)

private fun FetchStudyRoomDetailsResponse.SeatResponse.toModel(): StudyRoom.Seat = StudyRoom.Seat(
    id = this.id,
    row = this.row,
    column = this.column,
    number = this.number,
    type = this.type?.toModel(),
    status = StudyRoom.Seat.Status.valueOf(this.status),
    isMine = this.isMine,
    student = this.student?.toModel(),
)

private fun FetchStudyRoomDetailsResponse.SeatResponse.SeatTypeResponse.toModel(): StudyRoom.Seat.Type =
    StudyRoom.Seat.Type(
        id = this.id,
        name = this.name,
        color = this.color,
    )

private fun FetchStudyRoomDetailsResponse.SeatResponse.StudentResponse.toModel(): StudyRoom.Seat.Student =
    StudyRoom.Seat.Student(
        id = this.id,
        name = this.name,
    )

internal fun FetchAppliedStudyRoomResponse.toModel(): AppliedStudyRoom = AppliedStudyRoom(
    floor = this.floor,
    name = this.name,
)

internal fun FetchSeatTypesResponse.toModel(): List<StudyRoom.Seat.Type> =
    this.types.map(FetchSeatTypesResponse.SeatTypeResponse::toModel)

private fun FetchSeatTypesResponse.SeatTypeResponse.toModel(): StudyRoom.Seat.Type = StudyRoom.Seat.Type(
    id = this.id,
    name = this.name,
    color = this.color,
)

internal fun FetchAvailableStudyRoomTimesResponse.toModel(): List<AvailableStudyRoomTime> =
    this.availableStudyRoomTimes.map(FetchAvailableStudyRoomTimesResponse.AvailableStudyRoomTimeResponse::toModel)

private fun FetchAvailableStudyRoomTimesResponse.AvailableStudyRoomTimeResponse.toModel(): AvailableStudyRoomTime =
    AvailableStudyRoomTime(
        id = this.id,
        startTime = this.startTime,
        endTime = this.endTime,
    )
