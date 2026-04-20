package team.aliens.dms.android.data.latestudy.repository

import team.aliens.dms.android.data.latestudy.model.StudyType
import TeacherResponse

interface LateStudyRepository {

    suspend fun fetchStudyTypes(): List<StudyType>

    suspend fun fetchTeachers(): List<TeacherResponse>
}
