package team.aliens.dms_android.feature.register.event.id

import team.aliens.dms_android.base.MviEvent
import team.aliens.domain.model.student.ExamineStudentNumberOutput

sealed class SetIdEvent : MviEvent {
    data class ExamineGradeName(
        val examineStudentNumberOutput: ExamineStudentNumberOutput,
    ) : SetIdEvent()

    object DuplicateIdSuccess : SetIdEvent()
    object ExamineGradeBadRequestException : SetIdEvent()
    object ExamineGradeNotFoundException : SetIdEvent()
    object ExamineGradeConflictException : SetIdEvent()
    object ExamineGradeTooManyRequestException : SetIdEvent()
    object ExamineGradeInterServerException : SetIdEvent()
    object DuplicateIdBadRequestException : SetIdEvent()
    object DuplicateIdConflictException : SetIdEvent()
    object DuplicateIdTooManyRequestException : SetIdEvent()
    object DuplicateIdInterServerException : SetIdEvent()
    object UnknownException : SetIdEvent()
}
