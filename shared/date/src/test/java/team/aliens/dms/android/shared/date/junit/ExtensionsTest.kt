package team.aliens.dms.android.shared.date.junit

import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import team.aliens.dms.android.shared.date.toEpochMilli
import java.util.Date

class ExtensionsTest {
    private var mEpochMillis: Long = -1
    private var mEpochSecond: Long = -1
    private lateinit var mLocalDateTime: LocalDateTime
    private lateinit var mLocalDate: LocalDate
    private lateinit var mDate: Date

    @Before
    fun setupDateMembers() {
        this.mEpochMillis = 1147113418000L
        this.mEpochSecond = 1147113418L
        this.mLocalDateTime = LocalDateTime.of(2006, 5, 8, 18, 36, 58)
        this.mLocalDate = LocalDate.of(2006, 5, 8)
        this.mDate = Date(2006, 5, 8)

        check(mEpochMillis != -1L)
        check(mEpochSecond != -1L)
        check(::mLocalDateTime.isInitialized)
        check(::mLocalDate.isInitialized)
        check(::mDate.isInitialized)
    }

    @Test
    fun `Test LocalDateTime to Epoch Milliseconds`() {
        val zone = ZoneOffset.UTC

        assert(mLocalDateTime.toEpochMilli(zone) == mEpochMillis)
    }
}
