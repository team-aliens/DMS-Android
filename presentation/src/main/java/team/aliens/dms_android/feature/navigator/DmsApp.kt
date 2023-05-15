package team.aliens.dms_android.feature.navigator

import androidx.compose.runtime.Composable
import team.aliens.dms_android.feature.application.DmsAppState
import team.aliens.dms_android.feature.application.rememberDmsAppState

@Composable
internal fun DmsApp(
    dmsAppState: DmsAppState = rememberDmsAppState(),
) {/*
    // todo remove
    val navController = rememberNavController()
    // todo remove
    val scaffoldState = rememberScaffoldState()

    Surface(
        modifier = Modifier.background(
            DormTheme.colors.background,
        )
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = { hostState ->
                DormToastHost(
                    hostState = hostState,
                )
            },
        ) {
            NavHost(
                navController = navController,
                startDestination = route,
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
    }*/
}
