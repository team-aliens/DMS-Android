package team.aliens.remote.model.student

import com.google.gson.annotations.SerializedName

data class FetchMyPageResponse(
    @SerializedName("school_name") val schoolName: String,
    @SerializedName("name") val studentName: String,
    @SerializedName("gcn") val gradeClassNumber: String,
    @SerializedName("profile_image_url") val profileImageUrl: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("bonus_point") val bonusPoint: Int,
    @SerializedName("minus_point") val minusPoint: Int,
    @SerializedName("phrase") val phrase: String,
)
