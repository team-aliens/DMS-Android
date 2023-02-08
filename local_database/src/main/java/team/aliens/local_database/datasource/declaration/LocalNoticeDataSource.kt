package team.aliens.local_database.datasource.declaration

import com.example.local_database.entity.notice.NoticeDetailRoomEntity
import com.example.local_database.entity.notice.NoticeListRoomEntity
import java.util.UUID

interface LocalNoticeDataSource {

    suspend fun saveNoticeDetail(noticeId: UUID, noticeDetailRoomEntity: NoticeDetailRoomEntity)

    suspend fun fetchNoticeDetail(noticeId: UUID): NoticeDetailRoomEntity

    suspend fun saveNoticeList(noticeListRoomEntity: NoticeListRoomEntity)

    suspend fun fetchNoticeList(order: String): NoticeListRoomEntity
}
