package com.example.local_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.local_database.entity.studyroom.FetchApplyTimeRoomEntity

@Dao
interface StudyRoomDao {

    @Query("SELECT * FROM ApplyTime")
    suspend fun fetchApplyTime(): FetchApplyTimeRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplyTime(fetchApplyTimeRoomEntity: FetchApplyTimeRoomEntity)
}