package team.aliens.dms_android.handler

import android.app.Application
import android.content.Context
import android.util.Log
import javax.inject.Inject
import kotlin.system.exitProcess

internal class DmsExceptionHandler @Inject constructor(
    private val context: Context,
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(
        thread: Thread,
        exception: Throwable,
    ) {
        // 디버그용 로그
        Log.w(
            thread.name,
            "uncaughtException: ${exception.message}\n" + exception.printStackTrace(),
        )
        exitProcess(-1)
    }
}

internal fun Application.initExceptionHandler() {
    Thread.setDefaultUncaughtExceptionHandler(
        DmsExceptionHandler(
            context = applicationContext,
        ),
    )
}
