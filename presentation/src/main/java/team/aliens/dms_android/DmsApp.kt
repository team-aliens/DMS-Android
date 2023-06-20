package team.aliens.dms_android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.DormToastLayout
import team.aliens.design_system.toast.LocalToast
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
        val navController = dmsAppState.navController
        val toastState = LocalToast.current

        DormToastLayout(
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
                    homeNavigation(
                        navController = navController,
                        scaffoldState = scaffoldState,
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
}
