package team.aliens.dms_android.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.design_system.theme.DormTheme

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val autoSignInAvailable by remember { mutableStateOf(mainViewModel.checkAutoSignInAvailable()) }
            val initialAvailableFeatures by remember { mutableStateOf(mainViewModel.fetchLocalAvailableFeatures()) }

            DormTheme {
                DmsApp(
                    isSignedIn = autoSignInAvailable,
                    initialAvailableFeatures = initialAvailableFeatures,
                )
            }
        }
    }
}
