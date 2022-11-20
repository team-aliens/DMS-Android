package com.example.dms_android.feature.register.event.id

import com.example.dms_android.base.MviEvent

sealed class SetIdEvent : MviEvent {
    object ExamineGradeSuccess : SetIdEvent()
    object DuplicateIdSuccess : SetIdEvent()
    object ExamineGradeBadRequest : SetIdEvent()
    object ExamineGradeNotFound : SetIdEvent()
    object ExamineConflict : SetIdEvent()
    object DuplicateIdConflict : SetIdEvent()
    data class ErrorMessage(val message: String) : SetIdEvent()
}
