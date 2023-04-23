package team.aliens.remote.model.auth

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.auth.CheckIdExistsOutput

data class CheckIdExistsResponse(
    @SerializedName("email") val email: String,
)

internal fun CheckIdExistsResponse.toDomain(): CheckIdExistsOutput {
    return CheckIdExistsOutput(
        email = email,
    )
}
