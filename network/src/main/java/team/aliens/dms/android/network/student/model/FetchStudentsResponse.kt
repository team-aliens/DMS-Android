package team.aliens.dms.android.network.student.model

import com.google.gson.annotations.SerializedName

data class FetchStudentsResponse(
    @SerializedName("students") val students: List<StudentResponse>,
) {
    data class StudentResponse(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("gcn") val gradeClassNumber: String,
        @SerializedName("profile_image_url") val profileImageUrl: String,
    )
}
