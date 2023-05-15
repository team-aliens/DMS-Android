package team.aliens.dms_android.handler

import android.content.Context
import androidx.navigation.NavHostController
import team.aliens.dms_android.feature.navigator.DmsRoute
import team.aliens.domain._exception.CommonException
import javax.inject.Inject

internal class DmsExceptionHandler @Inject constructor(
    private val context: Context,
    private val navController: NavHostController,
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(
        thread: Thread,
        exception: Throwable,
    ) {
        when (exception) {
            is CommonException.SignInRequired -> signInRequired()
        }
    }

    private fun signInRequired() {
        // todo add toast code here
        navController.navigateToSignInScreen()
    }
}

private fun NavHostController.navigateToSignInScreen() = this.navigate(DmsRoute.Auth.SignIn)
