package com.example.dms_android.feature.register.state.home

import androidx.compose.runtime.Composable
import com.example.dms_android.base.MviState
import java.time.LocalDate
import java.util.Arrays

data class MealState(
    val meal: String,
    var mealList: MealList = MealList(),
    var today: LocalDate = LocalDate.now(),
    var a: Int = 0,
    var b: Int = 0,
    var noticeBoolean: Boolean = false,
) : MviState {
    companion object {
        fun initial() = MealState(
            meal = "",
        )
    }
}

data class MealList(
    var breakfast: List<String> = listOf(""),
    var lunch: List<String> = listOf(""),
    var dinner: List<String> = listOf(""),
)
