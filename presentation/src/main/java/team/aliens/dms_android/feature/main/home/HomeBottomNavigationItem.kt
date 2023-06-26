package team.aliens.dms_android.feature.main.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import team.aliens.presentation.R

internal sealed class HomeBottomNavigationItem(
    val route: String,
    @DrawableRes val iconResId: Int,
    @StringRes val titleResId: Int,
) {
    object Home : HomeBottomNavigationItem(
        route = "home",
        iconResId = R.drawable.ic_home,
        titleResId = R.string.bottom_nav_home,
    )

    object Application : HomeBottomNavigationItem(
        route = "application",
        iconResId = R.drawable.ic_application,
        titleResId = R.string.bottom_nav_application,
    )

    object Notice : HomeBottomNavigationItem(
        route = "notice",
        iconResId = R.drawable.ic_notice,
        titleResId = R.string.bottom_nav_notices,
    )

    object MyPage : HomeBottomNavigationItem(
        route = "mypage",
        iconResId = R.drawable.ic_mypage,
        titleResId = R.string.bottom_nav_my_page,
    )
}
