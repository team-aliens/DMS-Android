package com.example.data.repository

import com.example.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import com.example.data.remote.response.studyroom.ApplySeatTimeResponse
import com.example.data.remote.response.studyroom.StudyRoomDetailResponse
import com.example.data.remote.response.studyroom.StudyRoomListResponse
import com.example.data.remote.response.studyroom.StudyRoomTypeResponse
import com.example.domain.entity.studyroom.ApplySeatTimeEntity
import com.example.domain.entity.studyroom.StudyRoomDetailEntity
import com.example.domain.entity.studyroom.StudyRoomListEntity
import com.example.domain.entity.studyroom.StudyRoomTypeEntity
import com.example.domain.repository.StudyRoomRepository
import javax.inject.Inject

class StudyRoomRepositoryImpl @Inject constructor(
    private val remoteStudyRoomDataSource: RemoteStudyRoomDataSource,

    ) : StudyRoomRepository {

    override suspend fun applySeat() =
        remoteStudyRoomDataSource.applySeat()

    override suspend fun fetchApplySeatTime(): ApplySeatTimeEntity =
        remoteStudyRoomDataSource.fetchApplySeatTime().toEntity()

    override suspend fun cancelApplySeat() =
        remoteStudyRoomDataSource.cancelApplySeat()

    override suspend fun fetchStudyRoomList(): StudyRoomListEntity =
        remoteStudyRoomDataSource.fetchStudyRoomList().toEntity()

    override suspend fun fetchStudyRoomType(): StudyRoomTypeEntity =
        remoteStudyRoomDataSource.fetchStudyRoomType().toEntity()

    override suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailEntity =
        remoteStudyRoomDataSource.fetchStudyRoomDetail(roomId).toEntity()

    private fun ApplySeatTimeResponse.toEntity() =
        ApplySeatTimeEntity(
            startAt = startAt,
            endAt = endAt,
        )

    private fun StudyRoomListResponse.toEntity() =
        StudyRoomListEntity(
            studyRooms = studyRooms.map { it.toEntity() }
        )

    private fun StudyRoomListResponse.StudyRoom.toEntity() =
        StudyRoomListEntity.StudyRoom(
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

    private fun String.toInt2() =
        if (this == "0") {
            "모든"
        } else {
            this
        }

    private fun String.toStr() =
        when (this) {
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
        StudyRoomTypeEntity(
            types = types.map { it.toEntity() }
        )

    private fun StudyRoomTypeResponse.Type.toEntity() =
        StudyRoomTypeEntity.Type(
            color = color,
            id = id,
            name = name,
        )

    private fun StudyRoomDetailResponse.toEntity() =
        StudyRoomDetailEntity(
            floor = floor,
            name = name,
            totalAvailableSeat = totalAvailableSeat,
            inUseHeadCount = inUseHeadCount,
            availableSex = availableSex,
            availableGrade = availableGrade,
            eastDescription = eastDescription,
            westDescription = westDescription,
            southDescription = southDescription,
            northDescription = northDescription,
            totalWidthSize = totalWidthSize,
            totalHeightSize = totalHeightSize,
            studyRoomSex = availableSex.toStr() as String,
            seats = seats.map { it.toEntity() }
        )

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

    private fun StudyRoomDetailResponse.Type.toEntity() =
        StudyRoomDetailEntity.Type(
            id = id.toString(),
            name = name,
            color = color,
        )

    private fun StudyRoomDetailResponse.Student.toEntity() =
        StudyRoomDetailEntity.Student(
            id = id.toString(),
            name = name,
        )
}

