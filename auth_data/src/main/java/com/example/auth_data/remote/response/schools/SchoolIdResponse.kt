package com.example.auth_data.remote.response.schools

import com.example.auth_domain.entity.SchoolIdEntity
import com.google.gson.annotations.SerializedName

data class SchoolIdResponse(
    @SerializedName("school_id") val schoolId: String
)

fun SchoolIdResponse.toEntity() =
    SchoolIdEntity(
        schoolId = schoolId
    )
