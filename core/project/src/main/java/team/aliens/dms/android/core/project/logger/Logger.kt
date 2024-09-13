// TODO: need to be checked

package team.aliens.dms.android.core.project.logger

import android.util.Log

internal interface Logger {
    val tag: String

    fun log(tag: String, type: LoggingType, message: String)

    fun info(tag: String, message: String) = log(
        tag = tag,
        type = LoggingType.INFO,
        message = message,
    )

    fun error(tag: String, message: String) = log(
        tag = tag,
        type = LoggingType.ERROR,
        message = message,
    )

    fun warn(tag: String, message: String) = log(
        tag = tag,
        type = LoggingType.WARN,
        message = message,
    )

    fun debug(tag: String, message: String) = log(
        tag = tag,
        type = LoggingType.DEBUG,
        message = message,
    )

    enum class LoggingType {
        INFO, ERROR, WARN, DEBUG,
        ;
    }
}

internal class LoggerImpl : Logger {

    override val tag: String = "Logger"

    override fun log(
        tag: String,
        type: Logger.LoggingType,
        message: String,
    ) {
        when (type) {
            Logger.LoggingType.INFO -> Log.i(tag, message)
            Logger.LoggingType.ERROR -> Log.e(tag, message)
            Logger.LoggingType.WARN -> Log.w(tag, message)
            Logger.LoggingType.DEBUG -> Log.d(tag, message)
        }
    }
}

object Logging : Logger by LoggerImpl() {
    fun i(tag: String = this.tag, message: String) = super.info(tag, message)
    fun e(tag: String = this.tag, message: String) = super.error(tag, message)
    fun w(tag: String = this.tag, message: String) = super.warn(tag, message)
    fun d(tag: String = this.tag, message: String) = super.debug(tag, message)
}
