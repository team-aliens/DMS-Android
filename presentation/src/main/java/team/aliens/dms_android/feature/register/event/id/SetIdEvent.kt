package team.aliens.dms_android.feature.register.event.id

import com.example.dms_android.base.MviEvent
import com.example.domain.entity.user.ExamineGradeEntity

sealed class SetIdEvent : MviEvent {
    data class ExamineGradeName(val examineGradeEntity: ExamineGradeEntity) : SetIdEvent()
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
