package team.aliens.dms.android.network.latestudy.model

data class FetchStudyTypesResponse(
    val types: List<StudyTypeResponse>,
)

data class StudyTypeResponse(
    val id: String,
    val name: String,
)
