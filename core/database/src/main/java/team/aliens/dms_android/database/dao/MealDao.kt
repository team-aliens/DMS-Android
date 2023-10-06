package team.aliens.dms_android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.aliens.dms_android.database.entity.MealEntity

@Dao
abstract class MealDao {

    @Query(
        """
            SELECT *
            FROM tbl_meals
            WHERE date = :date; 
        """,
    )
    abstract fun findByDate(date: String): MealEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(mealEntity: MealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveAll(vararg mealEntities: MealEntity)

    @Delete
    abstract fun deleteByDate(date: String)
}
