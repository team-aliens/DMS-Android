package team.aliens.dms_android.feature

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.design_system.theme.DormTheme
import team.aliens.dms_android.base.BaseActivity
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.constans.Extra
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

            var availableFeatures = staticCompositionLocalOf {
                emptyMap<String, Boolean>()
            }

            if (route != null) {
                DormTheme(
                    darkTheme = isSystemInDarkTheme(),
                ) {
                    availableFeatures = staticCompositionLocalOf {
                        mapOf(
                            Extra.isMealServiceEnabled to intent.getBooleanExtra(
                                Extra.isMealServiceEnabled, false,
                            ),
                            Extra.isNoticeServiceEnabled to intent.getBooleanExtra(
                                Extra.isNoticeServiceEnabled, false,
                            ),
                            Extra.isPointServiceEnabled to intent.getBooleanExtra(
                                Extra.isPointServiceEnabled, false,
                            ),
                            Extra.isStudyRoomEnabled to intent.getBooleanExtra(
                                Extra.isStudyRoomEnabled, false,
                            ),
                            Extra.isRemainServiceEnabled to intent.getBooleanExtra(
                                Extra.isRemainServiceEnabled, false,
                            ),
                        )
                    }

                    CompositionLocalProvider(
                        values = arrayOf(LocalAvailableFeatures provides availableFeatures.current),
                    ) {
                        RootDms(
                            route = route,
                        )
                    }
                }
            }
        }
    }

    override fun initView() {
        /* explicit blank */
    }
}