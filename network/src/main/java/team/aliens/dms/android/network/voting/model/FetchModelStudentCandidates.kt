package team.aliens.dms.android.network.voting.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchModelStudentCandidates(
    @SerializedName("id") val id: UUID,
    @SerializedName("student_gcn") val studentGcn: Long,
    @SerializedName("name") val name: String,
    @SerializedName("profile_image_url") val profileImageUrl: String,
)
