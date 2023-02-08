package team.aliens.local_database.datasource.declaration

import com.example.local_database.entity.studyroom.FetchApplyTimeRoomEntity

interface LocalStudyRoomDataSource {
    suspend fun fetchApplyTime(): FetchApplyTimeRoomEntity

    suspend fun saveApplyTime(fetchApplyTimeRoomEntity: FetchApplyTimeRoomEntity)
}