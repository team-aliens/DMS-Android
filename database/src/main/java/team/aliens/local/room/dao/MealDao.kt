package team.aliens.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.aliens.local.room.common.RoomProperty
import team.aliens.local.room.entity.MealEntity

@Dao
interface MealDao {

    companion object {
        const val TBL_NAME = RoomProperty.TableName.Meal
        const val C_DATE = RoomProperty.ColumnName.Meal.Date
    }

    @Query(
        """
            SELECT *
            FROM $TBL_NAME
            WHERE $C_DATE = :date; 
        """,
    )
    fun findByDate(
        date: String,
    ): MealEntity

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun saveOne(
        mealEntity: MealEntity,
    )

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun saveAll(
        vararg mealEntities: MealEntity,
    )
}
