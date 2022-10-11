package com.example.auth_domain.entity

data class UserVisibleEntity(
    val survey: Boolean,
    val notice: Boolean,
    val myPage: Boolean,
    val recentRoom: Boolean,
)
