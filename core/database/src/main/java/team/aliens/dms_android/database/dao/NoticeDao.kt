package team.aliens.dms_android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.aliens.dms_android.database.entity.NoticeEntity
import java.util.UUID

@Dao
abstract class NoticeDao {

    @Query(
        """
            SELECT *
            FROM tbl_notices
            WHERE id = :id;
        """,
    )
    abstract fun findById(id: UUID): NoticeEntity

    @Query(
        """
            SELECT *
            FROM tbl_notices;
        """
    )
    abstract fun findAll(): List<NoticeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(noticeEntity: NoticeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveAll(vararg noticeEntities: NoticeEntity)

    @Delete
    abstract fun deleteById(id: UUID)
}
