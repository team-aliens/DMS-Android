package team.aliens.data.repository

import java.util.UUID
import javax.inject.Inject
import team.aliens.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import team.aliens.data.remote.response.studyroom.ApplySeatTimeResponse
import team.aliens.data.remote.response.studyroom.CurrentStudyRoomOptionResponse
import team.aliens.data.remote.response.studyroom.StudyRoomAvailableTimeListResponse
import team.aliens.data.remote.response.studyroom.StudyRoomDetailResponse
import team.aliens.data.remote.response.studyroom.StudyRoomListResponse
import team.aliens.data.remote.response.studyroom.StudyRoomTypeResponse
import team.aliens.domain.entity.studyroom.ApplySeatTimeEntity
import team.aliens.domain.entity.studyroom.CurrentStudyRoomOptionEntity
import team.aliens.domain.entity.studyroom.SeatTypeEntity
import team.aliens.domain.entity.studyroom.StudyRoomAvailableTimeListEntity
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity
import team.aliens.domain.entity.studyroom.StudyRoomListEntity
import team.aliens.domain.param.ApplyStudyRoomParam
import team.aliens.domain.param.StudyRoomDetailParam
import team.aliens.domain.repository.StudyRoomRepository

class StudyRoomRepositoryImpl @Inject constructor(
    private val remoteStudyRoomDataSource: RemoteStudyRoomDataSource,
) : StudyRoomRepository {

    override suspend fun applySeat(
        applyStudyRoomParam: ApplyStudyRoomParam,
    ) {
        remoteStudyRoomDataSource.applySeat(
            seatId = applyStudyRoomParam.seatId,
            timeSlot = applyStudyRoomParam.timeSlot,
        )
    }

    override suspend fun fetchApplySeatTime(): ApplySeatTimeEntity =
        remoteStudyRoomDataSource.fetchApplySeatTime().toEntity()

    override suspend fun cancelApplySeat(
        seatId: String,
        timeSlot: UUID,
    ) {
        remoteStudyRoomDataSource.cancelApplySeat(
            seatId = seatId,
            timeSlot = timeSlot,
        )
    }

    override suspend fun fetchStudyRoomList(
        timeSlot: UUID,
    ): StudyRoomListEntity =
        remoteStudyRoomDataSource.fetchStudyRoomList(
            timeSlot = timeSlot,
        ).toEntity()

    override suspend fun fetchStudyRoomType(
        studyRoomId: UUID,
    ): SeatTypeEntity =
        remoteStudyRoomDataSource.fetchStudyRoomType(
            studyRoomId = studyRoomId,
        ).toEntity()

    override suspend fun fetchStudyRoomDetail(
        studyRoomDetailParam: StudyRoomDetailParam,
    ): StudyRoomDetailEntity {
        return remoteStudyRoomDataSource.fetchStudyRoomDetail(
            roomId = studyRoomDetailParam.roomId,
            timeSlot = studyRoomDetailParam.timeSlot,
        ).toEntity()
    }

    override suspend fun fetchCurrentStudyRoomOption(): CurrentStudyRoomOptionEntity =
        remoteStudyRoomDataSource.fetchCurrentStudyRoomOption().toEntity()

    override suspend fun fetchStudyRoomAvailableTimeList(): StudyRoomAvailableTimeListEntity =
        remoteStudyRoomDataSource.fetchStudyRoomAvailableTimeList().toEntity()


    private fun ApplySeatTimeResponse.toEntity() = ApplySeatTimeEntity(
        startAt = startAt,
        endAt = endAt,
    )

    private fun StudyRoomListResponse.toEntity() =
        StudyRoomListEntity(studyRooms = studyRooms.map { it.toEntity() })

    private fun StudyRoomListResponse.StudyRoom.toEntity() = StudyRoomListEntity.StudyRoom(
        availableGrade = availableGrade.toString().toInt2(),
        availableSex = availableSex,
        floor = floor,
        id = id,
        inUseHeadcount = inUseHeadcount,
        isMine = isMine,
        name = name,
        totalAvailableSeat = totalAvailableSeat,
        studyRoomSex = availableSex.toStr() as String,
    )

    private fun String.toInt2() = if (this == "0") {
        "모든"
    } else {
        this
    }

    private fun String.toStr() = when (this) {
        "ALL" -> {
            "남녀"
        }
        "MALE" -> {
            "남자"
        }
        "FEMALE" -> {
            "여자"
        }
        else -> {}
    }

    private fun StudyRoomTypeResponse.toEntity() =
        SeatTypeEntity(types = types.map { it.toEntity() })

    private fun StudyRoomTypeResponse.Type.toEntity() = SeatTypeEntity.Type(
        color = color,
        id = id,
        name = name,
    )

    private fun StudyRoomDetailResponse.toEntity() = StudyRoomDetailEntity(floor = floor,
        name = name,
        totalAvailableSeat = totalAvailableSeat,
        startTime = startTime,
        endTime = endTime,
        inUseHeadCount = inUseHeadCount,
        availableSex = availableSex,
        availableGrade = availableGrade.toString().toInt2(),
        eastDescription = eastDescription,
        westDescription = westDescription,
        southDescription = southDescription,
        northDescription = northDescription,
        totalWidthSize = totalWidthSize,
        totalHeightSize = totalHeightSize,
        studyRoomSex = availableSex.toStr() as String,
        seats = seats.map { it.toEntity() })

    private fun StudyRoomDetailResponse.Seat.toEntity() =
        StudyRoomDetailEntity.Seat(
            id = id.toString(),
            widthLocation = widthLocation,
            heightLocation = heightLocation,
            number = number,
            type = type?.toEntity(),
            status = status,
            isMine = isMine,
            student = student?.toEntity()
        )

    private fun StudyRoomDetailResponse.Type.toEntity() = StudyRoomDetailEntity.Type(
        id = id.toString(),
        name = name,
        color = color,
    )

    private fun StudyRoomDetailResponse.Student.toEntity() = StudyRoomDetailEntity.Student(
        id = id.toString(),
        name = name,
    )

    private fun CurrentStudyRoomOptionResponse.toEntity() =
        CurrentStudyRoomOptionEntity(
            floor = floor,
            name = name,
        )

    private fun StudyRoomAvailableTimeListResponse.toEntity() =
        StudyRoomAvailableTimeListEntity(
            timeSlots = this.timeSlots.map { it.toEntity() }
        )

    private fun StudyRoomAvailableTimeListResponse.AvailableTime.toEntity() =
        StudyRoomAvailableTimeListEntity.AvailableTime(
            id = this.id,
            startTime = this.startTime,
            endTime = this.endTime,
        )
}

