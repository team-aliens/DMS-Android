package team.aliens.dms_android.feature.signup.event.school

sealed class ExamineSchoolCodeEvent {
    object ExamineSchoolCodeSuccess : ExamineSchoolCodeEvent()
    object BadRequestException: ExamineSchoolCodeEvent()
    object MissMatchSchoolCode : ExamineSchoolCodeEvent()
    object TooManyRequestException: ExamineSchoolCodeEvent()
    object ServerException: ExamineSchoolCodeEvent()
    object UnknownException: ExamineSchoolCodeEvent()
}