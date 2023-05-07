package team.aliens.dms_android.feature.navigator

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.DormToastHost

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RootDms(
    route: String,
) {

    val navController = rememberNavController()

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
    }
}
