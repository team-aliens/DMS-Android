package team.aliens.dms_android.feature.navigator

import com.example.dms_android.R

sealed class BottomNavigationItem(var route: String, var iconResId: Int, var title: String) {
    object Meal :
        BottomNavigationItem(NavigationRoute.BottomNavigation.Meal, R.drawable.ic_house, "Meal")

    object Survey :
        BottomNavigationItem(NavigationRoute.BottomNavigation.Survey, R.drawable.ic_plus, "Survey")

    object Notice : BottomNavigationItem(NavigationRoute.BottomNavigation.Notice,
        R.drawable.ic_notice,
        "Notice")

    object MyPage : BottomNavigationItem(NavigationRoute.BottomNavigation.MyPage,
        R.drawable.ic_mypage,
        "MyPage")
}
