package com.example.data.remote.response.schools

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class SchoolIdResponse(
    @SerializedName("school_id") val schoolId: UUID
)
