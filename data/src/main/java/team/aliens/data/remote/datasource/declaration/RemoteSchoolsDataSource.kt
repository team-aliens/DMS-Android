package team.aliens.data.remote.datasource.declaration

import com.example.data.remote.response.schools.SchoolConfirmQuestionResponse
import com.example.data.remote.response.schools.SchoolIdResponse
import java.util.UUID

interface RemoteSchoolsDataSource {

    suspend fun schoolQuestion(
        schoolId: UUID
    ): SchoolConfirmQuestionResponse

    suspend fun compareSchoolAnswer(
        schoolId: UUID,
        answer: String,
    )

    suspend fun examineSchoolCode(
        schoolCode: String
    ): SchoolIdResponse
}