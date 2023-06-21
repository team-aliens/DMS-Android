package team.aliens.dms_android.feature.main.home

import team.aliens.presentation.R

internal sealed class HomeBottomNavigationItem(
    val route: String,
    val iconResId: Int,
    val titleResId: Int,
) {
    object Meal : HomeBottomNavigationItem(
        route = "meal",
        iconResId = R.drawable.ic_home,
        titleResId = R.string.Home,
    )

    object Application : HomeBottomNavigationItem(
        route = "application",
        iconResId = R.drawable.ic_application,
        titleResId = R.string.Application,
    )

    object Notice : HomeBottomNavigationItem(
        route = "notice",
        iconResId = R.drawable.ic_notice,
        titleResId = R.string.Notice,
    )

    object MyPage : HomeBottomNavigationItem(
        route = "mypage",
        iconResId = R.drawable.ic_mypage,
        titleResId = R.string.MyPage,
    )
}
