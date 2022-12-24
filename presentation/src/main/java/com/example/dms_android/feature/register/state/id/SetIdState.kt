package com.example.dms_android.feature.register.state.id

import com.example.dms_android.base.MviState

data class SetIdState(
    val id: String
) : MviState {
    companion object {
        fun initial() = SetIdState(
            id = ""
        )
    }
}