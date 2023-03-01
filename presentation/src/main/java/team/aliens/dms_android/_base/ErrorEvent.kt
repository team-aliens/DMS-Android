package team.aliens.dms_android._base

sealed class ErrorEvent {

    object BadRequest : ErrorEvent()

    object NullPointer : ErrorEvent()

    object Unauthorized : ErrorEvent()

    object NoInternet : ErrorEvent()

    object TooManyRequests : ErrorEvent()

    object InternalServerError : ErrorEvent()

    object Unknown : ErrorEvent()
}
