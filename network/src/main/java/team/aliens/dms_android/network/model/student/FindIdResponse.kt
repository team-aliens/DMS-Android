package team.aliens.dms_android.network.model.student

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.student.FindIdOutput

data class FindIdResponse(
    @SerializedName("email") val email: String,
)

internal fun FindIdResponse.toDomain(): FindIdOutput {
    return FindIdOutput(
        email = this.email,
    )
}
