package team.aliens.dms.android.feature.main.navigation

import team.aliens.dms.android.feature.main.announcement.navigation.AnnouncementNavigator
import team.aliens.dms.android.feature.main.application.navigation.ApplicationNavigator
import team.aliens.dms.android.feature.main.home.navigation.HomeNavigator
import team.aliens.dms.android.feature.main.mypage.navigation.MyPageNavigator

interface MainNavigator :
    AnnouncementNavigator,
    ApplicationNavigator,
    HomeNavigator,
    MyPageNavigator {

    fun openHome()

    fun openApplication()

    fun openAnnouncementList()

    fun openMyPage()
}
