package team.aliens.dms_android.feature.navigator

import team.aliens.presentation.R

sealed class BottomNavigationItem(
    val route: String,
    val iconResId: Int,
    val titleResId: Int,
) {
    object Meal :
        BottomNavigationItem(
            route = NavigationRoute.BottomNavigation.Meal,
            iconResId = R.drawable.ic_home,
            titleResId = R.string.Home,
        )

    object Application :
        BottomNavigationItem(
            route = NavigationRoute.BottomNavigation.Application,
            iconResId = R.drawable.ic_application,
            titleResId = R.string.Application,
        )

    object Notice : BottomNavigationItem(
        route = NavigationRoute.BottomNavigation.Notice,
        iconResId = R.drawable.ic_notice,
        titleResId = R.string.Notice,
    )

    object MyPage : BottomNavigationItem(
        route = NavigationRoute.BottomNavigation.MyPage,
        iconResId = R.drawable.ic_mypage,
        titleResId = R.string.MyPage,
    )
}
