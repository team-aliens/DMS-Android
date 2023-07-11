package team.aliens.dms_android.feature.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import java.util.UUID
import team.aliens.dms_android.extension.appendNavArgumentName
import team.aliens.dms_android.feature.main.editpassword.editPasswordNavigation
import team.aliens.dms_android.feature.main.home.Home
import team.aliens.dms_android.feature.main.home.announcements.NoticeDetailsScreen
import team.aliens.dms_android.feature.main.image.EditProfileImageScreen
import team.aliens.dms_android.feature.main.notificationbox.NotificationBoxScreen
import team.aliens.dms_android.feature.main.pointhistory.PointHistoryScreen
import team.aliens.dms_android.feature.main.remains.RemainsApplicationScreen
import team.aliens.dms_android.feature.main.studyroom.StudyRoomDetailsScreen
import team.aliens.dms_android.feature.main.studyroom.StudyRoomsScreen
import team.aliens.dms_android.util.SelectImageType

internal object MainNavigation {
    const val route = "main"

    const val Home = this.route + "/home"
    const val StudyRooms = this.route + "/studyRooms"
    const val StudyRoomDetails = this.route + "/studyRoomDetails"
    const val NoticeDetails = this.route + "/noticeDetails"
    const val RemainsApplication = this.route + "/remainsApplication"
    const val PointHistory = this.route + "/pointHistory"
    const val UploadProfileImage = this.route + "/uploadProfileImage"
    const val NotificationBox = this.route + "/notificationBox"

    object Arguments {
        const val StudyRoomId = "study-room-id"
        const val Timeslot = "timeslot"
        const val NoticeId = "notice-id"
        const val SelectImageType = "select-image-type"
    }
}

internal fun NavGraphBuilder.mainNavigation(
    onNavigateToStudyRooms: () -> Unit,
    onNavigateToRemainsApplication: () -> Unit,
    onNavigateToNoticeDetails: (noticeId: UUID) -> Unit,
    onNavigateToUploadProfileImageWithTakingPhoto: () -> Unit,
    onNavigateToUploadProfileImageWithSelectingPhoto: () -> Unit,
    onNavigateToPointHistory: () -> Unit,
    onNavigateToEditPasswordNav: () -> Unit,
    onNavigateToAuthNav: () -> Unit,
    onPrevious: () -> Unit,
    onNavigateToStudyRoomDetails: (
        studyRoomId: UUID,
        studyRoomAvailableTimeId: UUID,
    ) -> Unit,
    onNavigateToEditPasswordSetPassword: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToNotificationBox: () -> Unit,
) {
    navigation(
        startDestination = MainNavigation.Home,
        route = MainNavigation.route,
    ) {
        composable(MainNavigation.Home) {
            Home(
                onNavigateToStudyRooms = onNavigateToStudyRooms,
                onNavigateToRemainsApplication = onNavigateToRemainsApplication,
                onNavigateToNoticeDetails = onNavigateToNoticeDetails,
                onNavigateToUploadProfileImageWithTakingPhoto = onNavigateToUploadProfileImageWithTakingPhoto,
                onNavigateToUploadProfileImageWithSelectingPhoto = onNavigateToUploadProfileImageWithSelectingPhoto,
                onNavigateToPointHistory = onNavigateToPointHistory,
                onNavigateToEditPasswordNav = onNavigateToEditPasswordNav,
                onNavigateToAuthNav = onNavigateToAuthNav,
                onNavigateToNotificationBox = onNavigateToNotificationBox,
            )
        }
        composable(MainNavigation.StudyRooms) {
            StudyRoomsScreen(
                onPrevious = onPrevious,
                onNavigateToStudyRoomDetails = onNavigateToStudyRoomDetails,
            )
        }
        composable(
            route = MainNavigation.StudyRoomDetails appendNavArgumentName MainNavigation.Arguments.StudyRoomId appendNavArgumentName MainNavigation.Arguments.Timeslot,
            arguments = listOf(
                navArgument(MainNavigation.Arguments.StudyRoomId) {
                    type = NavType.StringType
                },
                navArgument(MainNavigation.Arguments.Timeslot) {
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            backStackEntry.arguments?.run {
                val roomId = getString(MainNavigation.Arguments.StudyRoomId)!!
                val timeslot = getString(MainNavigation.Arguments.Timeslot)!!

                StudyRoomDetailsScreen(
                    onPrevious = onPrevious,
                    roomId = UUID.fromString(roomId),
                    timeSlot = UUID.fromString(timeslot),
                )
            }
        }
        composable(
            route = MainNavigation.NoticeDetails appendNavArgumentName MainNavigation.Arguments.NoticeId,
            arguments = listOf(
                navArgument(MainNavigation.Arguments.NoticeId) {
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            backStackEntry.arguments?.run {
                val noticeId = getString(MainNavigation.Arguments.NoticeId)!!

                NoticeDetailsScreen(
                    onPrevious = onPrevious,
                    noticeId = UUID.fromString(noticeId),
                )
            }
        }
        composable(MainNavigation.RemainsApplication) {
            RemainsApplicationScreen(
                onPrevious = onPrevious,
            )
        }
        composable(MainNavigation.PointHistory) {
            PointHistoryScreen(
                onPrevious = onPrevious,
            )
        }
        composable(
            route = MainNavigation.UploadProfileImage appendNavArgumentName MainNavigation.Arguments.SelectImageType,
            arguments = listOf(
                navArgument(MainNavigation.Arguments.SelectImageType) {
                    type = NavType.StringType
                },
            ),
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.run {
                val selectImageType = getString(MainNavigation.Arguments.SelectImageType)!!

                EditProfileImageScreen(
                    selectImageType = SelectImageType.valueOf(selectImageType),
                    onPrevious = onPrevious,
                )
            }
        }
        composable(MainNavigation.NotificationBox) {
            NotificationBoxScreen(
                onPrevious = onPrevious,
            )
        }
        editPasswordNavigation(
            onNavigateToEditPasswordSetPassword = onNavigateToEditPasswordSetPassword,
            onPrevious = onPrevious,
            onNavigateToHome = onNavigateToHome,
        )
    }
}
