package team.aliens.dms.android.app

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import team.aliens.dms.android.DmsApp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.jwt.di.IsJwtAvailable
import team.aliens.dms.android.core.notification.DeviceTokenManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @IsJwtAvailable
    @Inject
    lateinit var isJwtAvailable: StateFlow<Boolean>

    @Inject
    lateinit var deviceTokenManager: DeviceTokenManager

    private val updateLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult(),
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            // TODO :: 업데이트 취소 or 실패 시 강제 업데이트 모달
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )

        checkAppUpdate()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)
//          TODO : 적응형 레이아웃 적용

            DmsTheme {
                DmsApp(
                    windowSizeClass = windowSizeClass,
                )
            }
        }

        lifecycleScope.launch {
            deviceTokenManager.fetchDeviceToken()
        }
    }

    private fun checkAppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            val updateOptions = AppUpdateOptions
                .newBuilder(AppUpdateType.IMMEDIATE)
                .setAllowAssetPackDeletion(true)
                .build()

            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                updateLauncher,
                updateOptions,
            )
        }
    }
}
