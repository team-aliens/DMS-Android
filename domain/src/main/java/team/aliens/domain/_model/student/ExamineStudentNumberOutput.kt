package team.aliens.domain._model.student

/**
 * A response returned when examining student's number, contains user's name
 * @property name name of student, matching with student's number
 */
data class ExamineStudentNumberOutput(
    val name: String,
)
