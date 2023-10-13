package team.aliens.dms.android.network.model.auth

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.domain.model.auth.CheckIdExistsOutput

data class CheckIdExistsResponse(
    @SerializedName("email") val email: String,
)

internal fun CheckIdExistsResponse.toDomain(): CheckIdExistsOutput {
    return CheckIdExistsOutput(
        email = email,
    )
}
