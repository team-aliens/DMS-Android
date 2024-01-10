package team.aliens.dms.android.database.meal

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DatabaseMealDataSourceTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Before
    fun setup() {
        rule.inject()
    }
}
