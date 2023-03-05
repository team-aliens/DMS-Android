package team.aliens.dms_android.feature

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.design_system.theme.DormTheme
import team.aliens.dms_android.base.BaseActivity
import team.aliens.dms_android.feature.navigator.RootDms
import team.aliens.presentation.R
import team.aliens.presentation.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val secondIntent = intent
            val route = secondIntent.getStringExtra("route")
            if (route != null) {
                DormTheme(
                    darkTheme = isSystemInDarkTheme(),
                ) {
                    RootDms(route)
                }
            }
        }
    }

    override fun initView() {
        /* explicit blank */
    }
}