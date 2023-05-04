package team.aliens.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.aliens.local.room.common.RoomProperty
import team.aliens.local.room.entity.NoticeEntity
import java.util.*

@Dao
interface NoticeDao {

    companion object {
        const val TBL_NAME = RoomProperty.TableName.Notice
        const val C_ID = RoomProperty.ColumnName.Notice.Id
    }

    @Query(
        """
            SELECT *
            FROM $TBL_NAME
            WHERE $C_ID = :id;
        """
    )
    fun findOne(
        id: UUID,
    ): NoticeEntity

    @Query(
        """
            SELECT *
            FROM $TBL_NAME;
        """
    )
    fun findAll(): List<NoticeEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun saveOne(
        noticeEntity: NoticeEntity,
    )

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun saveAll(
        vararg noticeEntities: NoticeEntity,
    )
}
