package team.aliens.dms.android.network.latestudy.model

data class SubmitLateStudyRequest(
    val teacher_id: String,
    val type_id: String,
    val reason: String,
    val start_date: String,
    val end_date: String,
)
