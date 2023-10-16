package team.aliens.dms.android.feature._legacy.base

@Deprecated("legacy")
sealed class ErrorEvent {

    object BadRequest : ErrorEvent()

    object NullPointer : ErrorEvent()

    object Unauthorized : ErrorEvent()

    object NoInternet : ErrorEvent()

    object TooManyRequests : ErrorEvent()

    object InternalServerError : ErrorEvent()

    object TimeOut : ErrorEvent()

    object Unknown : ErrorEvent()
}