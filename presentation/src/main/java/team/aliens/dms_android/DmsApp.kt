package team.aliens.dms_android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.DormToastHost
import team.aliens.dms_android.feature.auth.authNavigation
import team.aliens.dms_android.feature.home.homeNavigation
import team.aliens.dms_android.feature.signup.signUpNavigation

@Composable
internal fun DmsApp(
    initialRoute: String,
    dmsAppState: DmsAppState,
) {
    Surface(
        modifier = Modifier.background(
            color = DormTheme.colors.background,
        ),
    ) {
        val scaffoldState = dmsAppState.scaffoldState

        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = { hostState ->
                DormToastHost(
                    hostState = hostState,
                )
            },
        ) { paddingValues ->

            val navController = dmsAppState.navController

            NavHost(
                modifier = Modifier.padding(
                    paddingValues = paddingValues,
                ),
                navController = navController,
                startDestination = initialRoute,
            ) {
                homeNavigation(
                    navController = navController,
                    scaffoldState = scaffoldState,
                    appState = dmsAppState,
                )
                signUpNavigation(
                    navController = navController,
                )
                authNavigation(
                    dmsAppState = dmsAppState,
                    navController = navController,
                )
            }
        }
    }
}
