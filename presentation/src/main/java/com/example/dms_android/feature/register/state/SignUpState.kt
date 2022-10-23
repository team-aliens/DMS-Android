package com.example.dms_android.feature.register.state

import com.example.dms_android.base.MviState

data class SignUpState(
    val schoolCode: String,
    val schoolAnswer: String,
    val email: String,
    val authCode: String,
    val grade: Int,
    val classRoom: Int,
    val number: Int,
    val accountId: String,
    val password: String,
    val profileImageUrl: String,
) : MviState {
    companion object {
        fun initial() =
            SignUpState(
                schoolCode = "",
                schoolAnswer = "",
                email = "",
                authCode = "",
                grade = 0,
                classRoom = 0,
                number = 0,
                accountId = "",
                password = "",
                profileImageUrl = "",
            )
    }
}