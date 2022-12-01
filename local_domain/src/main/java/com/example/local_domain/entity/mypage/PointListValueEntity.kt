package com.example.local_domain.entity.mypage

import java.util.UUID

data class PointListValueEntity(
    val pointId: UUID,
    val date: String,
    val pointLocalType: String,
    val name: String,
    val score: Int,
)

