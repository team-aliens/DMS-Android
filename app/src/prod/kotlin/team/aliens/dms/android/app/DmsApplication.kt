package team.aliens.dms.android.app

import android.app.Application
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DmsApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        preloadFonts()
    }

    private fun preloadFonts() {
        // Preload fonts to prevent text rendering delay
        try {
            val fontIds = listOf(
                team.aliens.dms.android.core.designsystem.R.font.noto_sans_kr_black,
                team.aliens.dms.android.core.designsystem.R.font.noto_sans_kr_bold,
                team.aliens.dms.android.core.designsystem.R.font.noto_sans_kr_light,
                team.aliens.dms.android.core.designsystem.R.font.noto_sans_kr_medium,
                team.aliens.dms.android.core.designsystem.R.font.noto_sans_kr_regular,
                team.aliens.dms.android.core.designsystem.R.font.noto_sans_kr_thin,
            )
            fontIds.forEach { fontId ->
                ResourcesCompat.getFont(this, fontId)
            }
        } catch (e: Exception) {
            // Font preloading failed, but continue app initialization
        }
    }
}
