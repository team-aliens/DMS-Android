package com.example.dms_android.feature.auth.login

sealed class SignInViewEffect {
    object SignInSuccess : SignInEvent()
    object BadRequestException : SignInEvent()
    object UnAuthorizedException : SignInEvent()
    object NotFoundException : SignInEvent()
    object TooManyRequestException : SignInEvent()
    object InternalServerException : SignInEvent()
    object UnKnownException : SignInEvent()
}
