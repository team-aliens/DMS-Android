package com.example.auth_data.remote.response.schools

import com.example.auth_domain.entity.SchoolConfirmQuestionEntity
import com.google.gson.annotations.SerializedName

data class SchoolConfirmQuestionResponse(
    @SerializedName("question") val question: String
)

fun SchoolConfirmQuestionResponse.toEntity() =
    SchoolConfirmQuestionEntity(
        question = question
    )