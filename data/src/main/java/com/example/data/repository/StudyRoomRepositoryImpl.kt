package com.example.data.repository

import com.example.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import com.example.data.remote.response.studyroom.ApplySeatTimeResponse
import com.example.data.remote.response.studyroom.StudyRoomListResponse
import com.example.data.remote.response.studyroom.StudyRoomTypeResponse
import com.example.data.util.OfflineCacheUtil
import com.example.domain.entity.studyroom.ApplySeatTimeEntity
import com.example.domain.entity.studyroom.StudyRoomListEntity
import com.example.domain.entity.studyroom.StudyRoomTypeEntity
import com.example.domain.repository.StudyRoomRepository
import com.example.local_database.datasource.declaration.LocalStudyRoomDataSource
import com.example.local_database.entity.studyroom.FetchApplyTimeRoomEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudyRoomRepositoryImpl @Inject constructor(
    private val remoteStudyRoomDataSource: RemoteStudyRoomDataSource,
    private val localStudyRoomDataSource: LocalStudyRoomDataSource,
) : StudyRoomRepository {

    override suspend fun applySeat() =
        remoteStudyRoomDataSource.applySeat()


    override suspend fun fetchApplySeatTime(): Flow<ApplySeatTimeEntity> =
        OfflineCacheUtil<ApplySeatTimeEntity>()
            .remoteData { remoteStudyRoomDataSource.fetchApplySeatTime().toEntity() }
            .createRemoteFlow()

    override suspend fun cancelApplySeat() =
        remoteStudyRoomDataSource.cancelApplySeat()

    override suspend fun fetchStudyRoomList(): Flow<StudyRoomListEntity> =
        OfflineCacheUtil<StudyRoomListEntity>()
            .remoteData { remoteStudyRoomDataSource.fetchStudyRoomList().toEntity() }
            .createRemoteFlow()

    override suspend fun fetchStudyRoomType(): Flow<StudyRoomTypeEntity> =
        OfflineCacheUtil<StudyRoomTypeEntity>()
            .remoteData { remoteStudyRoomDataSource.fetchStudyRoomType().toEntity() }
            .createRemoteFlow()


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
            availableGrade = availableGrade,
            availableSex = availableSex,
            floor = floor,
            id = id,
            inUseHeadcount = inUseHeadcount,
            isMine = isMine,
            name = name,
            totalAvailableSeat = totalAvailableSeat,
        )

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
}