package team.aliens.data.repository

import team.aliens.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import team.aliens.data.remote.response.studyroom.*
import team.aliens.domain.entity.studyroom.*
import team.aliens.domain.repository.StudyRoomRepository
import javax.inject.Inject

class StudyRoomRepositoryImpl @Inject constructor(
    private val remoteStudyRoomDataSource: RemoteStudyRoomDataSource,
) : StudyRoomRepository {

    override suspend fun applySeat(data: String) {
        remoteStudyRoomDataSource.applySeat(data)
    }

    override suspend fun fetchApplySeatTime(): ApplySeatTimeEntity =
        remoteStudyRoomDataSource.fetchApplySeatTime().toEntity()

    override suspend fun cancelApplySeat() {
        remoteStudyRoomDataSource.cancelApplySeat()
    }

    override suspend fun fetchStudyRoomList(): StudyRoomListEntity =
        remoteStudyRoomDataSource.fetchStudyRoomList().toEntity()

    override suspend fun fetchStudyRoomType(): SeatTypeEntity =
        remoteStudyRoomDataSource.fetchStudyRoomType().toEntity()

    override suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailEntity =
        remoteStudyRoomDataSource.fetchStudyRoomDetail(roomId).toEntity()

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
            timeSlots = this.timeSlots.map(StudyRoomAvailableTimeListResponse::toEntity())
        )

    private fun StudyRoomAvailableTimeListResponse.AvailableTime.toEntity() =
        StudyRoomAvailableTimeListEntity.AvailableTime(
            id = this.id,
            name = this.name,
        )
}

