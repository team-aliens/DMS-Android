package team.aliens.data.util

import org.threeten.bp.LocalDateTime
import java.util.Calendar

object LocalDateTimeEx {
    @JvmStatic
    fun getNow(): LocalDateTime = Calendar.getInstance().toLocalDateTime()
}

private fun Calendar.toLocalDateTime(): LocalDateTime = LocalDateTime.of(get(Calendar.YEAR), get(Calendar.MONTH) + 1, get(Calendar.DAY_OF_MONTH),
    get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE), get(Calendar.SECOND), get(Calendar.MILLISECOND) * 1000000)
