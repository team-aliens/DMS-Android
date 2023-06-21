package team.aliens.dms_android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.DormToastLayout
import team.aliens.design_system.toast.rememberToastState
import team.aliens.dms_android.navigation.authNavigation
import team.aliens.dms_android.navigation.mainNavigation

@Composable
internal fun DmsApp(
    modifier: Modifier = Modifier,
    dmsAppState: DmsAppState = rememberDmsAppState(),
) {
    val scaffoldState = dmsAppState.scaffoldState
    val navController = dmsAppState.navController
    val toastState = rememberToastState()

    DormToastLayout(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = DormTheme.colors.background,
            ),
        toastState = toastState,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
        ) { paddingValues ->
            NavHost(
                modifier = Modifier.padding(
                    paddingValues = paddingValues,
                ),
                navController = navController,
                startDestination = initialRoute,
            ) {
                mainNavigation()
                authNavigation()
            }
        }
    }
}

