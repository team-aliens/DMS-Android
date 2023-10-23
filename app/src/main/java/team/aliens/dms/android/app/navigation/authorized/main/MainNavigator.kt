package team.aliens.dms.android.app.navigation.authorized.main

import androidx.navigation.NavController
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.NavGraphSpec
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavGraph
import team.aliens.dms.android.app.navigation.unauthorized.UnauthorizedNavGraph
import team.aliens.dms.android.feature._legacy.util.SelectImageType
import team.aliens.dms.android.feature.destinations.AnnouncementListScreenDestination
import team.aliens.dms.android.feature.destinations.ApplicationScreenDestination
import team.aliens.dms.android.feature.destinations.EditProfileImageScreenDestination
import team.aliens.dms.android.feature.destinations.HomeScreenDestination
import team.aliens.dms.android.feature.destinations.MyPageScreenDestination
import team.aliens.dms.android.feature.destinations.NoticeDetailsScreenDestination
import team.aliens.dms.android.feature.destinations.NotificationBoxScreenDestination
import team.aliens.dms.android.feature.destinations.PointHistoryScreenDestination
import team.aliens.dms.android.feature.destinations.RemainsApplicationScreenDestination
import team.aliens.dms.android.feature.destinations.StudyRoomListScreenDestination
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavGraph
import team.aliens.dms.android.feature.main.announcement.navigation.AnnouncementNavigator
import team.aliens.dms.android.feature.main.application.navigation.ApplicationNavigator
import team.aliens.dms.android.feature.main.home.navigation.HomeNavigator
import team.aliens.dms.android.feature.main.mypage.navigation.MyPageNavigator
import java.util.UUID

class MainNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController,
) :
    MainSectionNavigator,
    AnnouncementNavigator,
    ApplicationNavigator,
    HomeNavigator,
    MyPageNavigator {

    override fun openHome() {
        navController.navigate(HomeScreenDestination within navGraph)
    }

    override fun openApplication() {
        navController.navigate(ApplicationScreenDestination within navGraph)
    }

    override fun openAnnouncementList() {
        navController.navigate(AnnouncementListScreenDestination within navGraph)
    }

    override fun openMyPage() {
        navController.navigate(MyPageScreenDestination within navGraph)
    }

    override fun openNoticeDetails(noticeId: UUID) {
        navController.navigate(NoticeDetailsScreenDestination(noticeId = noticeId) within navGraph)
    }

    override fun openStudyRoomList() {
        navController.navigate(StudyRoomListScreenDestination within navGraph)
    }

    override fun openRemainsApplication() {
        navController.navigate(RemainsApplicationScreenDestination within navGraph)
    }

    override fun openNotificationBox() {
        navController.navigate(NotificationBoxScreenDestination within navGraph)
    }

    override fun openPointHistory() {
        navController.navigate(PointHistoryScreenDestination within navGraph)
    }

    override fun openEditPasswordNav() {
        navController.navigate(EditPasswordNavGraph) {
            restoreState = true
            launchSingleTop = true
        }
    }

    override fun openEditProfileImage(selectImageType: SelectImageType) {
        // TODO
        navController.navigate(EditProfileImageScreenDestination(selectImageType) within navGraph)
    }

    override fun openUnauthorizedNav() {
        navController.navigate(UnauthorizedNavGraph) {
            popUpTo(AuthorizedNavGraph) {
                inclusive = true
            }
        }
    }
}
