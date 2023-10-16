package team.aliens.dms.android.network._legacy.model.student

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.student.FindIdOutput

data class FindIdResponse(
    @SerializedName("email") val email: String,
)

internal fun FindIdResponse.toDomain(): FindIdOutput {
    return FindIdOutput(
        email = this.email,
    )
}
