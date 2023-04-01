package team.aliens.remote.model.student

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.student.FindIdOutput

data class FindIdResponse(
    @SerializedName("email") val email: String,
)

internal fun FindIdResponse.toDomain(): FindIdOutput {
    return FindIdOutput(
        email = this.email,
    )
}
