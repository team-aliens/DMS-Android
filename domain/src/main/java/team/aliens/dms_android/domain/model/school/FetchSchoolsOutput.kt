package team.aliens.dms_android.domain.model.school

import java.util.*

/**
 * A response returned when fetching list of schools
 * @property schools list of schools
 */
data class FetchSchoolsOutput(
    val schools: List<SchoolInformation>,
) {

    /**
     * An information set of school
     * @property id the school's unique id
     * @property name the school's name
     * @property address the school's address
     */
    data class SchoolInformation(
        val id: UUID? = null,
        val name: String,
        val address: String,
    )
}
