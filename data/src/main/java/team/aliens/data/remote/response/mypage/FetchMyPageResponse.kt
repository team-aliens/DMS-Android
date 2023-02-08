package team.aliens.data.remote.response.mypage

import com.google.gson.annotations.SerializedName
import team.aliens.domain.entity.mypage.MyPageEntity

data class FetchMyPageResponse(
    @SerializedName("school_name") val schoolName: String,
    @SerializedName("name") val name: String,
    @SerializedName("gcn") val gcn: String,
    @SerializedName("profile_image_url") val profileImageUrl: String,
    @SerializedName("bonus_point") val bonusPoint: Int,
    @SerializedName("minus_point") val minusPoint: Int,
    @SerializedName("phrase") val phrase: String,
)

fun FetchMyPageResponse.toEntity() = MyPageEntity(
    schoolName = schoolName,
    name = name,
    gcn = gcn,
    profileImageUrl = profileImageUrl,
    bonusPoint = bonusPoint,
    minusPoint = minusPoint,
    phrase = phrase,
)
