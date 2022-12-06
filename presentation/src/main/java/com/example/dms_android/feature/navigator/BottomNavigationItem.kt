package com.example.dms_android.feature.navigator

import com.example.dms_android.R

sealed class BottomNavigationItem(var route: String, var iconResId: Int, var title: String) {
    object Meal : BottomNavigationItem("meal", R.drawable.ic_house, "Meal")
    object Survey : BottomNavigationItem("survey", R.drawable.ic_plus, "Survey")
    object Notice : BottomNavigationItem("notice", R.drawable.ic_notice, "Notice")
    object MyPage : BottomNavigationItem("myPage", R.drawable.ic_mypage, "MyPage")
}
