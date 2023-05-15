package team.aliens.dms_android.feature.navigator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.DormToastHost
import team.aliens.dms_android.feature.application.DmsAppState
import team.aliens.dms_android.feature.application.rememberDmsAppState

@Composable
internal fun DmsApp(
    dmsAppState: DmsAppState = rememberDmsAppState(),
) {

    Surface(
        modifier = Modifier.background(
            DormTheme.colors.background,
        )
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
                startDestination = DmsRoute.Auth.route, // fixme change to DmsRoute.Home.route
            ) {
                homeNavigation(
                    navController = navController,
                    scaffoldState = scaffoldState,
                )

                signUpNavigation(
                    navController = navController,
                )

                authNavigation(
                    navController = navController,
                )
            }
        }
    }
}
