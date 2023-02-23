package team.aliens.dms_android.feature.navigator

import team.aliens.presentation.R

sealed class BottomNavigationItem(var route: String, var iconResId: Int, var title: String) {
    object Meal :
        BottomNavigationItem(
            route = NavigationRoute.BottomNavigation.Meal,
            iconResId = R.drawable.ic_house,
            title = "Meal",
        )

    object Application :
        BottomNavigationItem(
            route = NavigationRoute.BottomNavigation.Application,
            iconResId = R.drawable.ic_plus,
            title = "Application",
        )

    object Notice : BottomNavigationItem(
        route = NavigationRoute.BottomNavigation.Notice,
        iconResId = R.drawable.ic_notice,
        title = "Notice",
    )

    object MyPage : BottomNavigationItem(
        route = NavigationRoute.BottomNavigation.MyPage,
        iconResId = R.drawable.ic_mypage,
        title = "MyPage",
    )
}
