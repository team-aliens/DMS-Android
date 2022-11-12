package com.example.local_domain.entity.mypage

import java.util.UUID

data class PointListEntity(
    val pointId: UUID,
    val date: String,
    val pointLocalType: String,
    val name: String,
    val score: Int,
)

