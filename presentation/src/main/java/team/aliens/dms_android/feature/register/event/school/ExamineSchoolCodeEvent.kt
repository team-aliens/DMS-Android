package team.aliens.dms_android.feature.register.event.school

sealed interface ExamineSchoolCodeEvent

object ExamineSchoolCodeSuccess : ExamineSchoolCodeEvent
object MissMatchSchoolCode : ExamineSchoolCodeEvent