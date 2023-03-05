package team.aliens.data.remote.response.students

import com.google.gson.annotations.SerializedName

data class FetchSchoolsResponse(
    @SerializedName("schools") val schools: List<SchoolResponse>,
)
