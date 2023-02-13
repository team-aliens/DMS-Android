package team.aliens.dms_android.feature.navigator

import team.aliens.presentation.R

sealed class BottomNavigationItem(var route: String, var iconResId: Int, var title: String) {
    object Meal :
        BottomNavigationItem(NavigationRoute.BottomNavigation.Meal, R.drawable.ic_house, "Meal")

    object Application :
        BottomNavigationItem(NavigationRoute.BottomNavigation.Application, R.drawable.ic_plus, "Application")

    object Notice : BottomNavigationItem(
        NavigationRoute.BottomNavigation.Notice,
        R.drawable.ic_notice,
        "Notice"
    )

    object MyPage : BottomNavigationItem(
        NavigationRoute.BottomNavigation.MyPage,
        R.drawable.ic_mypage,
        "MyPage"
    )
}
