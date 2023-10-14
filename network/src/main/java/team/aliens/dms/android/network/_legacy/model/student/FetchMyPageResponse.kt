package team.aliens.dms.android.network._legacy.model.student

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model._common.Sex
import team.aliens.dms.android.domain.model.student.FetchMyPageOutput

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

internal fun FetchMyPageResponse.toDomain(): FetchMyPageOutput {
    return FetchMyPageOutput(
        schoolName = this.schoolName,
        studentName = this.studentName,
        gradeClassNumber = this.gradeClassNumber,
        profileImageUrl = this.profileImageUrl,
        sex = Sex.valueOf(this.sex),
        bonusPoint = this.bonusPoint,
        minusPoint = this.minusPoint,
        phrase = this.phrase,
    )
}
