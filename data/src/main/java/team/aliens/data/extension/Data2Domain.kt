package team.aliens.data.extension

import team.aliens.data.remote.response.students.FetchSchoolsResponse
import team.aliens.data.remote.response.students.SchoolResponse
import team.aliens.domain.entity.schools.SchoolEntity

internal fun SchoolResponse.toDomain(): SchoolEntity {
    return SchoolEntity(
        id = this.id,
        name = this.name,
        address = this.address,
    )
}

internal fun FetchSchoolsResponse.toDomain(): List<SchoolEntity> {
    return this.schools.map { it.toDomain() }
}
