package team.aliens.dms_android.handler

import android.content.Context
import team.aliens.dms_android.feature.application.DmsAppState
import team.aliens.dms_android.feature.application.navigateToSignIn
import team.aliens.domain.exception.CommonException
import javax.inject.Inject
import kotlin.system.exitProcess

internal class DmsExceptionHandler @Inject constructor(
    private val context: Context,
    private val appState: DmsAppState,
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(
        thread: Thread,
        exception: Throwable,
    ) {
        when (exception) {
            is CommonException.SignInRequired -> signInRequired()
            else -> {
                exitProcess(0)
            }
        }
    }

    private fun signInRequired() {
        appState::navigateToSignIn
    }
}
