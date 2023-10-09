package team.aliens.dms_android.network.model.school

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.domain.model.school.FetchSchoolsOutput
import java.util.*

data class FetchSchoolsResponse(
    @SerializedName("schools") val schools: List<SchoolResponse>,
) {
    data class SchoolResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("name") val name: String,
        @SerializedName("address") val address: String,
    )
}

internal fun FetchSchoolsResponse.toDomain(): FetchSchoolsOutput {
    return FetchSchoolsOutput(
        schools = this.schools.toDomain(),
    )
}

internal fun FetchSchoolsResponse.SchoolResponse.toDomain(): FetchSchoolsOutput.SchoolInformation {
    return FetchSchoolsOutput.SchoolInformation(
        id = this.id,
        name = this.name,
        address = this.address,
    )
}

internal fun List<FetchSchoolsResponse.SchoolResponse>.toDomain(): List<FetchSchoolsOutput.SchoolInformation> {
    return this.map(FetchSchoolsResponse.SchoolResponse::toDomain)
}
