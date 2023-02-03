package com.example.dms_android.feature.register.event.home

import com.example.dms_android.base.MviEvent

sealed class MealEvent : MviEvent {
    object MealSuccess : MealEvent()
    object BadRequestException : MealEvent()
    object UnAuthorizedException : MealEvent()
    object ForbiddenException : MealEvent()
    object TooManyRequestException : MealEvent()
    object InternalServerException : MealEvent()
    object UnknownException : MealEvent()
}