package team.aliens.dms.android.app

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.view.WindowCompat
import com.google.accompanist.adaptive.calculateDisplayFeatures
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.jwt.di.IsJwtAvailable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @IsJwtAvailable
    @Inject
    lateinit var isJwtAvailable: StateFlow<Boolean>

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )

        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)
            val displayFeatures = calculateDisplayFeatures(activity = this)

            DmsTheme {
                DmsApp(
                    windowSizeClass = windowSizeClass,
                    displayFeatures = displayFeatures,
                    isJwtAvailable = isJwtAvailable,
                )
            }
        }
    }
}
