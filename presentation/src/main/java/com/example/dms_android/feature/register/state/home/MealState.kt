package com.example.dms_android.feature.register.state.home

import com.example.dms_android.base.MviState

data class MealState(
    val meal: String,
) : MviState {
    companion object {
        fun initial() = MealState(
            meal = ""
        )
    }
}
