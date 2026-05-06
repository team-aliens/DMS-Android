package team.aliens.dms.android.data.latestudy.repository

import team.aliens.dms.android.data.latestudy.model.StudyType
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest
import team.aliens.dms.android.network.latestudy.model.TeacherResponse

interface LateStudyRepository {

    suspend fun fetchStudyTypes(): List<StudyType>

    suspend fun fetchTeachers(): List<TeacherResponse>

    suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusResponse

    suspend fun submitLateStudy(request: SubmitLateStudyRequest)
}
