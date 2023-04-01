package team.aliens.local.room.common

internal object RoomProperty {

    object DataBase {
        const val DbVersion = 1
    }

    object TableName {
        const val Meal = "tbl_meals"
        const val Notice = "tbl_notices"
    }

    object ColumnName {

        object Meal {
            const val Date = "date"
            const val Breakfast = "breakfast"
            const val Lunch = "lunch"
            const val Dinner = "dinner"
        }

        object Notice {
            const val Id = "id"
            const val Title = "title"
            const val Content = "content"
            const val CreatedAt = "created_at"
        }
    }
}
