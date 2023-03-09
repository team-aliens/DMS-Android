package team.aliens.domain._param.schools.queryschools

import java.util.*

/**
 * @author junsuPark
 * A response returned when querying list of schools
 * [schools] list of schools
 */
data class QuerySchoolsResponse(
    val schools: List<SchoolInformation>,
) {

    /**
     * @author junsuPark
     * An information set of school
     * [id] the school's unique id
     * [name] the school's name
     * [address] the school's address
     */
    data class SchoolInformation(
        val id: UUID,
        val name: String,
        val address: String,
    )
}
