package team.aliens.local_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.aliens.local_database.entity.notice.NoticeDetailRoomEntity
import team.aliens.local_database.entity.notice.NoticeListRoomEntity
import team.aliens.local_database.tablename.TableName
import java.util.*

@Dao
interface NoticeDao {

    @Query("SELECT * FROM ${TableName.Notice.NOTICE_LIST} ORDER BY :order")
    suspend fun fetchNoticeList(order: String): NoticeListRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNoticeList(noticeListRoomEntity: NoticeListRoomEntity)

    @Query("SELECT * FROM ${TableName.Notice.NOTICE_DETAIL} WHERE noticeId = :noticeId")
    suspend fun fetchNoticeDetail(noticeId: UUID): NoticeDetailRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNoticeDetail(noticeDetailRoomEntity: NoticeDetailRoomEntity)
}