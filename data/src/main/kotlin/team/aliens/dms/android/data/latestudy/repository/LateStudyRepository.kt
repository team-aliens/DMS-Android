package team.aliens.dms.android.data.latestudy.repository

import team.aliens.dms.android.data.latestudy.model.StudyType
import TeacherResponse
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest

interface LateStudyRepository {

    suspend fun fetchStudyTypes(): List<StudyType>

    suspend fun fetchTeachers(): List<TeacherResponse>

    suspend fun submitLateStudy(request: SubmitLateStudyRequest)
}
