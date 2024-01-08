package team.aliens.dms.android.shared.date.junit

import org.junit.Before
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.util.Date

class ExtensionsTest {
    private lateinit var mLocalDateTime: LocalDateTime
    private lateinit var mLocalDate: LocalDate
    private lateinit var mDate: Date

    @Before
    fun setupDateMembers() {
        this.mLocalDateTime = LocalDateTime.now()
        this.mLocalDate = LocalDate.now()
        this.mDate = Date()

        check(::mLocalDateTime.isInitialized)
        check(::mLocalDate.isInitialized)
        check(::mDate.isInitialized)
    }
}
