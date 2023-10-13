package team.aliens.dms.android.core.project.logger

fun logInfo(tag: String = "Information", message: String) = Logging.info(tag, message)

fun logError(tag: String = "Error", message: String) = Logging.error(tag, message)

fun logWarn(tag: String = "Warning", message: String) = Logging.warn(tag, message)

fun logDebug(tag: String = "Debug", message: String) = Logging.debug(tag, message)
