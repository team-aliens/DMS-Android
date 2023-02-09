package team.aliens.local_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.aliens.local_database.entity.mypage.PointListRoomEntity
import team.aliens.local_database.tablename.TableName

@Dao
interface PointDao {

    @Query("SELECT * FROM ${TableName.POINT} WHERE pointLocalType = :pointLocalType")
    suspend fun fetchPointList(pointLocalType: String): List<PointListRoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePointList(pointLocalValue: List<PointListRoomEntity>)
}
