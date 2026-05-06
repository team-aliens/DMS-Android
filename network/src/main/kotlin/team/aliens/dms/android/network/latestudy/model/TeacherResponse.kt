package team.aliens.dms.android.network.latestudy.model

data class FetchTeachersResponse(
    val teachers: List<TeacherResponse>,
)

data class TeacherResponse(
    val id: String,
    val name: String,
)
