package team.aliens.domain.repository

import kotlinx.coroutines.flow.Flow
import team.aliens.domain.entity.schools.SchoolEntity
import team.aliens.domain.entity.user.SchoolConfirmQuestionEntity
import team.aliens.domain.entity.user.SchoolIdEntity
import team.aliens.domain.param.SchoolAnswerParam
import java.util.*

interface SchoolsRepository {

    suspend fun schoolQuestion(
        schoolId: UUID,
    ): Flow<SchoolConfirmQuestionEntity>

    suspend fun compareSchoolAnswer(
        schoolAnswerParam: SchoolAnswerParam,
    )

    suspend fun examineSchoolCode(
        schoolCode: String,
    ): SchoolIdEntity

    suspend fun fetchSchools(): List<SchoolEntity>
}