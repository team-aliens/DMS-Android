package team.aliens.local_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import team.aliens.local_database.entity.meal.MealRoomEntity
import team.aliens.local_database.tablename.TableName

@Dao
interface MealDao {

    @Query("SELECT * FROM ${TableName.MEAL_LIST} WHERE date = :date")
    suspend fun fetchMealList(date: String): MealRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMealList(mealRoomEntity: List<MealRoomEntity>)
}
