package com.example.data.remote.api

import com.example.data.remote.response.schools.SchoolIdResponse
import com.example.data.remote.response.schools.SchoolConfirmQuestionResponse
import com.example.data.remote.url.DmsUrl
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface SchoolsApi {

    @GET(DmsUrl.Schools.schoolQuestion)
    suspend fun schoolQuestion(
        @Path("school-id") schoolId: UUID
    ): SchoolConfirmQuestionResponse

    @GET(DmsUrl.Schools.schoolAnswer)
    suspend fun compareSchoolAnswer(
        @Path("school-id") schoolId: UUID,
        @Query("answer") answer: String,
    )

    @GET(DmsUrl.Schools.schoolCode)
    suspend fun examineSchoolCode(
        @Query("school_code") schoolCode: String
    ): SchoolIdResponse
}
