package com.example.dms_android.feature.register.state.school

import com.example.dms_android.base.MviState

data class CompareSchoolAnswerState(
    val schoolAnswer: String,
) : MviState {
    companion object {
        fun initial() =
            CompareSchoolAnswerState(
                schoolAnswer = "",
            )
    }
}
