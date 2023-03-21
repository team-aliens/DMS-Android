package team.aliens.domain._model.school

import java.util.*

/**
 * @author junsuPark
 * A response returned when examining school school verification code
 * @property schoolId a school id can be used at sign up, etc
 */
data class ExamineSchoolVerificationCodeResult(
    val schoolId: UUID,
)
