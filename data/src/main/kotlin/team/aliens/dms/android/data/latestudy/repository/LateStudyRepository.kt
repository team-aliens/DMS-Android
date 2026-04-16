package team.aliens.dms.android.domain.latestudy.repository

import team.aliens.dms.android.domain.latestudy.model.StudyType

interface LateStudyRepository {
    suspend fun fetchStudyTypes(): List<StudyType>
}
