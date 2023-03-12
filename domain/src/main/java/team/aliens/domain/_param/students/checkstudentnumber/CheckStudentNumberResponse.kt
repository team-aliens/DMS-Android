package team.aliens.domain._param.students.checkstudentnumber

/**
 * @author junsuPark
 * A response returned when checking student's number, contains user's name
 * @property name name of student, matching with student's number
 */
data class CheckStudentNumberResponse(
    val name: String,
)
