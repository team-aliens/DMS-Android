package com.example.dms_android.feature.register.state.school

import com.example.dms_android.base.MviState

data class ConfirmSchoolState(
    val school: String
) : MviState {
    companion object {
        fun initial() =
            ConfirmSchoolState(
                school = ""
            )
    }
}

