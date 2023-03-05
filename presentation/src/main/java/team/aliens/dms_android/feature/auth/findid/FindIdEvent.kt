package team.aliens.dms_android.feature.auth.findid


sealed interface FindIdEvent


object SuccessFindId : FindIdEvent

object FindIdBadRequest : FindIdEvent

object FindIdUnauthorized : FindIdEvent

object FindIdNotFound : FindIdEvent

object FindIdTooManyRequest : FindIdEvent

object FindIdServerException : FindIdEvent

object FindIdNoInternetException : FindIdEvent

object FindIdUnknownException : FindIdEvent