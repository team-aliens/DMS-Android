package team.aliens.dms.android.app.navigation

import androidx.annotation.DrawableRes
import androidx.navigation3.runtime.NavKey
import team.aliens.dms.android.app.navigation.ApplicationScreenNav
import team.aliens.dms.android.app.navigation.HomeScreenNav
import team.aliens.dms.android.app.navigation.MyPageScreenNav
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon

sealed class BottomMenu(
    val route: NavKey,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    val title: String,
) {
    data object Home : BottomMenu(
        route = HomeScreenNav,
        icon = DmsIcon.Home,
        selectedIcon = DmsIcon.HomeFill,
        title = "홈",
    )

    data object Application : BottomMenu(
        route = ApplicationScreenNav,
        icon = DmsIcon.CheckCircle,
        selectedIcon = DmsIcon.CheckCircleFill,
        title = "신청",
    )

    data object MyPage : BottomMenu(
        route = MyPageScreenNav,
        icon = DmsIcon.MyPage,
        selectedIcon = DmsIcon.MyPageFill,
        title = "마이페이지",
    )
}
