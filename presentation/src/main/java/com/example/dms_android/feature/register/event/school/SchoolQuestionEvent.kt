package com.example.dms_android.feature.register.event.school

import com.example.dms_android.base.MviEvent


sealed class SchoolQuestionEvent : MviEvent {
    object SchoolQuestionSuccess : SchoolQuestionEvent()
    object BadRequestException : SchoolQuestionEvent()
    object NotFoundException : SchoolQuestionEvent()
    object TooManyRequestException : SchoolQuestionEvent()
    object InternalServerException : SchoolQuestionEvent()
    object UnknownException : SchoolQuestionEvent()
}