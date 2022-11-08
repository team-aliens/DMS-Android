package com.example.dms_android.feature.register.state.school

import com.example.dms_android.base.MviState

data class ExamineSchoolCodeState(
    val schoolCode: String
) : MviState {
    companion object {
        fun initial() =
            ExamineSchoolCodeState(
                schoolCode = ""
            )
    }
}