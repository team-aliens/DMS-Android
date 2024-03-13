package team.aliens.dms.android.network.school.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchSchoolsResponse(
    @SerializedName("schools") val schools: List<SchoolResponse>,
) {
    data class SchoolResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("name") val name: String,
        @SerializedName("address") val address: String,
    )
}
