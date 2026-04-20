import com.google.gson.annotations.SerializedName

data class TeacherResponse(
    @SerializedName("teacher_id")
    val teacherId: String,

    @SerializedName("teacher_name")
    val teacherName: String,
)
