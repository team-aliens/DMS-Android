package team.aliens.dms.android.shared.date.junit

import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import team.aliens.dms.android.shared.date.toDate
import team.aliens.dms.android.shared.date.toEpochMilli
import team.aliens.dms.android.shared.date.toLocalDate
import team.aliens.dms.android.shared.date.toLocalDateTime
import java.util.Date

class ExtensionsTest {
    private var mEpochMillis: Long = -1
    private var mEpochSecond: Long = -1
    private lateinit var mLocalDateTime: LocalDateTime
    private lateinit var mLocalDate: LocalDate
    private lateinit var mDate: Date
    private lateinit var mDateTime: Date
    private lateinit var mZoneOffset: ZoneOffset
    private lateinit var mIsoDateTimeFormat: String
    private lateinit var mIsoDateFormat: String

    @Before
    fun setupDateMembers() {
        this.mEpochMillis = 1147113414000L
        this.mEpochSecond = 1147046400L
        this.mLocalDateTime = LocalDateTime.of(2006, 5, 8, 18, 36, 54)
        this.mLocalDate = LocalDate.of(2006, 5, 8)
        this.mDate = Date(2006, 5, 8)
        this.mDateTime = Date(2006, 5, 8, 18, 36, 54)
        this.mZoneOffset = ZoneOffset.UTC
        this.mIsoDateTimeFormat = "2006-05-08T18:36:54"
        this.mIsoDateFormat = "2006-05-08"

        check(mEpochMillis != -1L)
        check(mEpochSecond != -1L)
        check(::mLocalDateTime.isInitialized)
        check(::mLocalDate.isInitialized)
        check(::mDate.isInitialized)
        check(::mZoneOffset.isInitialized)
        check(::mIsoDateTimeFormat.isInitialized)
        check(::mIsoDateFormat.isInitialized)
    }

    @Test
    fun `Test LocalDateTime to Epoch Millisecond`() {
        assert(mLocalDateTime.toEpochMilli(mZoneOffset) == mEpochMillis)
    }

    @Test
    fun `Test EpochMilliseconds to LocalDateTime`() {
        assert(mEpochMillis.toLocalDateTime(mZoneOffset) == mLocalDateTime)
    }

    @Test
    fun `Test EpochDay to LocalDate`() {
        assert(mEpochSecond.toLocalDate() == mLocalDate)
    }

    @Test
    fun `Test String to LocalDateTime`() {
        assert(mIsoDateTimeFormat.toLocalDateTime() == mLocalDateTime)
    }

    @Test
    fun `Test String to LocalDate`() {
        assert(mIsoDateFormat.toLocalDate() == mLocalDate)
    }

    @Test
    fun `Test LocalDate to Date`() {
        assert(mLocalDate.toDate() == mDate)
    }

    @Test
    fun `Test LocalDateTime to Date`() {
        assert(mLocalDateTime.toDate() == mDateTime)
    }
}
