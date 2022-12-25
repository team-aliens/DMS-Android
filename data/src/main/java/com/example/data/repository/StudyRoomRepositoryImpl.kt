package com.example.data.repository

import com.example.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import com.example.data.remote.response.studyroom.ApplySeatTimeResponse
import com.example.data.util.OfflineCacheUtil
import com.example.domain.entity.studyroom.ApplySeatTimeEntity
import com.example.domain.repository.StudyRoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudyRoomRepositoryImpl @Inject constructor(
    private val remoteStudyRoomDataSource: RemoteStudyRoomDataSource,
) : StudyRoomRepository {

    override suspend fun applySeat() =
        remoteStudyRoomDataSource.applySeat()

    override suspend fun fetchApplySeatTime(): Flow<ApplySeatTimeEntity> =
        OfflineCacheUtil<ApplySeatTimeEntity>()
            .remoteData { remoteStudyRoomDataSource.fetchApplySeatTime().toEntity() }
            .createRemoteFlow()

    override suspend fun cancelApplySeat() =
        remoteStudyRoomDataSource.cancelApplySeat()


    private fun ApplySeatTimeResponse.toEntity() =
        ApplySeatTimeEntity(
            startAt = startAt,
            endAt = endAt,
        )
}