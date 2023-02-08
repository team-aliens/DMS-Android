package team.aliens.dms_android.feature.cafeteria

import team.aliens.dms_android.base.MviEvent

sealed class MealEvent : MviEvent {
    object MealSuccess : MealEvent()
    object BadRequestException : MealEvent()
    object UnAuthorizedException : MealEvent()
    object ForbiddenException : MealEvent()
    object TooManyRequestException : MealEvent()
    object InternalServerException : MealEvent()
    object UnknownException : MealEvent()
}