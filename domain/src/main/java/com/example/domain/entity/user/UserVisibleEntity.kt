package com.example.domain.entity.user

data class UserVisibleEntity(
    val survey: Boolean,
    val notice: Boolean,
    val myPage: Boolean,
    val recentRoom: Boolean,
)
