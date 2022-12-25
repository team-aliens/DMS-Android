package com.example.local_database.datasource.declaration

import com.example.local_database.entity.mypage.PointListRoomEntity
import com.example.local_database.entity.studyroom.FetchApplyTimeRoomEntity

interface LocalStudyRoomDataSource {
    //TODO: 나중에 합쳐지면 그냥 Entitiy로 바꿔야 함
    suspend fun fetchApplyTime(): FetchApplyTimeRoomEntity

    suspend fun saveApplyTime(fetchApplyTimeRoomEntity: FetchApplyTimeRoomEntity)
}