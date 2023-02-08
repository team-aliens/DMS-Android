package team.aliens.local_database.tablename

object TableName {

    const val MEAL_LIST = "mealList"

    const val NOTICE = "notice"

    const val POINT = "point"

    object Notice {
        const val NOTICE_LIST = "${NOTICE}List"
        const val NOTICE_DETAIL = "${NOTICE}Detail"
    }
}