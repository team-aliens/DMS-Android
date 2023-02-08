package team.aliens.dms_android.feature.register.event.school

import com.example.dms_android.base.MviEvent


sealed class ExamineSchoolCodeEvent : MviEvent {
    object ExamineSchoolCodeSuccess : ExamineSchoolCodeEvent()
    object BadRequestException : ExamineSchoolCodeEvent()
    object UnAuthorizedException : ExamineSchoolCodeEvent()
    object TooManyRequestException : ExamineSchoolCodeEvent()
    object InternalServerException : ExamineSchoolCodeEvent()
    object UnknownException : ExamineSchoolCodeEvent()
}