package team.aliens.dms.android.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.aliens.dms.android.core.database.entity.NoticeEntity
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
        """,
    )
    abstract fun findAll(): List<NoticeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(notice: NoticeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveAll(notices: List<NoticeEntity>)
}
