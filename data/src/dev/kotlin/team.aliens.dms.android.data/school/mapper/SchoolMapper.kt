package team.aliens.dms.android.data.school.mapper

import team.aliens.dms.android.data.school.model.School
import team.aliens.dms.android.network.school.model.FetchSchoolsResponse

internal fun FetchSchoolsResponse.toModel(): List<School> =
    this.schools.map(FetchSchoolsResponse.SchoolResponse::toModel)

private fun FetchSchoolsResponse.SchoolResponse.toModel(): School = School(
    id = this.id,
    name = this.name,
    address = this.address,
)
