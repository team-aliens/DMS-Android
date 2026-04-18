package team.aliens.dms.android.data.latestudy.repository

import team.aliens.dms.android.data.latestudy.model.StudyType

interface LateStudyRepository {
    suspend fun fetchStudyTypes(): List<StudyType>
}
