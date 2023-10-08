package team.aliens.dms_android._feature.main.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import team.aliens.dms_android.presentation.R

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

    object Announcement : HomeBottomNavigationItem(
        route = "announcement",
        iconResId = R.drawable.ic_notice,
        titleResId = R.string.bottom_nav_announcement,
    )

    object MyPage : HomeBottomNavigationItem(
        route = "mypage",
        iconResId = R.drawable.ic_mypage,
        titleResId = R.string.bottom_nav_my_page,
    )
}
