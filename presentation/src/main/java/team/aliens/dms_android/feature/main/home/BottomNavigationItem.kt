package team.aliens.dms_android.feature.main.home

import team.aliens.presentation.R

sealed class BottomNavigationItem(
    val route: String,
    val iconResId: Int,
    val titleResId: Int,
) {
    object Meal : BottomNavigationItem(
        route = "meal",
        iconResId = R.drawable.ic_home,
        titleResId = R.string.Home,
    )

    object Application : BottomNavigationItem(
        route = "application",
        iconResId = R.drawable.ic_application,
        titleResId = R.string.Application,
    )

    object Notice : BottomNavigationItem(
        route = "notice",
        iconResId = R.drawable.ic_notice,
        titleResId = R.string.Notice,
    )

    object MyPage : BottomNavigationItem(
        route = "mypage",
        iconResId = R.drawable.ic_mypage,
        titleResId = R.string.MyPage,
    )
}
