package team.aliens.dms_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.design_system.theme.DormTheme

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val refreshTokenAvailable by rememberSaveable { mutableStateOf(mainViewModel.checkRefreshTokenAvailable()) }
            val localAvailableFeatures by rememberSaveable { mutableStateOf(mainViewModel.fetchLocalAvailableFeatures()) }

            DormTheme {
                DmsApp(
                    refreshTokenAvailable = refreshTokenAvailable,
                )
            }
        }
    }
}
