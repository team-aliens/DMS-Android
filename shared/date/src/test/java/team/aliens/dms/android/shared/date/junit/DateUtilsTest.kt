package team.aliens.dms.android.shared.date.junit

import org.junit.Test
import org.threeten.bp.LocalDate
import team.aliens.dms.android.shared.date.util.now
import team.aliens.dms.android.shared.date.util.today

class DateUtilsTest {

    @Test
    fun `Test Today`() {
        val today = today
        val testToday = LocalDate.now()

        assert(today == testToday)
        assert(today == testToday.plusDays(5).minusDays(5))
        assert(today == testToday.minusDays(802).plusDays(401).plusDays(401))

        assert(today != testToday.plusDays(5))
        assert(today != testToday.minusDays(7))
    }

    @Test
    fun `Test Now`() {
        val now = now
        val testNow = now

        assert(now == testNow)
        assert(now == testNow.plusSeconds(5).minusSeconds(5))
        assert(now == testNow.minusDays(504).plusDays(500).plusDays(4))

        assert(now != testNow.plusNanos(1))
        assert(now != testNow.minusNanos(1))
    }
}
