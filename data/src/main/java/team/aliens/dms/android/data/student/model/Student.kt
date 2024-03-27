package team.aliens.dms.android.data.student.model

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("gcn") val gradeClassNumber: String,
    @SerializedName("profile_image_url") val profileImageUrl: String,
)
