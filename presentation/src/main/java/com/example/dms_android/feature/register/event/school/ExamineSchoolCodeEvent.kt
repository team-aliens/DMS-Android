package com.example.dms_android.feature.register.event.school

import com.example.dms_android.base.MviEvent
import java.util.UUID


sealed class ExamineSchoolCodeEvent: MviEvent {

    data class InputSchoolCode(val schoolCode: String) : ExamineSchoolCodeEvent()

    data class ExamineSchoolCodeSuccess(val schoolId: UUID) : ExamineSchoolCodeEvent()
    object BadRequestException : ExamineSchoolCodeEvent()
    object UnAuthorizedException : ExamineSchoolCodeEvent()
    object TooManyRequestException : ExamineSchoolCodeEvent()
    object InternalServerException : ExamineSchoolCodeEvent()
    object UnknownException : ExamineSchoolCodeEvent()
}