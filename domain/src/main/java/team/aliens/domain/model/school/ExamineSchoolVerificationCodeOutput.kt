package team.aliens.domain.model.school

import java.util.*

/**
 * A response returned when examining school school verification code
 * @property schoolId a school id can be used at sign up, etc
 */
data class ExamineSchoolVerificationCodeOutput(
    val schoolId: UUID,
)
