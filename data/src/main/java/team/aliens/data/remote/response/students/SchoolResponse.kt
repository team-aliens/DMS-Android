package team.aliens.data.remote.response.students

import com.google.gson.annotations.SerializedName
import java.util.*

data class SchoolResponse(
    @SerializedName("id") val id: UUID,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
)
