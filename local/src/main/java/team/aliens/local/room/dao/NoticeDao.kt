package team.aliens.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import team.aliens.local.room.common.RoomProperty
import team.aliens.local.room.entity.NoticeEntity
import java.util.*

@Dao
internal interface NoticeDao {

    companion object {
        const val TBL_NAME = RoomProperty.TableName.Notice
        const val C_ID = RoomProperty.ColumnName.Notice.Id
    }

    @Query(
        """
            SELECT *
            FROM $TBL_NAME;
        """
    )
    fun findAll(): List<NoticeEntity>

    @Query(
        """
            SELECT $C_ID
            FROM $TBL_NAME
            WHERE $C_ID = :id;
        """
    )
    fun findOne(
        id: UUID,
    ): NoticeEntity

    @Insert
    fun save(
        noticeEntity: NoticeEntity,
    )
}
