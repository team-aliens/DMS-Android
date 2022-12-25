package com.example.data.repository

import com.example.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import com.example.data.remote.response.studyroom.ApplySeatTimeResponse
import com.example.data.util.OfflineCacheUtil
import com.example.domain.entity.studyroom.ApplySeatTimeEntity
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
                //TODO: 나중에 Local모듈 합쳐지면 짜야 하는 코드 일단은 RemoteFlow발행 하게 해놓음
            /*.localData { localStudyRoomDataSource.fetchApplyTime() }
            .doOnNeedRefresh { localStudyRoomDataSource.saveApplyTime(it) }
            .createFlow()*/
            .createRemoteFlow()

    override suspend fun cancelApplySeat() =
        remoteStudyRoomDataSource.cancelApplySeat()


    private fun ApplySeatTimeResponse.toEntity() =
        ApplySeatTimeEntity(
            startAt = startAt,
            endAt = endAt,
        )
}