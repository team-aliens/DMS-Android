package team.aliens.dms_android.feature.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import java.util.UUID
import team.aliens.dms_android.common.UuidType
import team.aliens.dms_android.feature.main.editpassword.ConfirmPasswordScreen
import team.aliens.dms_android.feature.main.editpassword.EditPasswordSetPasswordScreen
import team.aliens.dms_android.feature.main.home.Home
import team.aliens.dms_android.feature.main.home.mypage.pointhistory.PointHistoryScreen
import team.aliens.dms_android.feature.main.home.notice.NoticeDetailsScreen
import team.aliens.dms_android.feature.main.image.UploadProfileImageScreen
import team.aliens.dms_android.feature.main.remains.RemainsApplicationScreen
import team.aliens.dms_android.feature.main.studyroom.StudyRoomDetailsScreen
import team.aliens.dms_android.feature.main.studyroom.StudyRoomsScreen
import team.aliens.dms_android.util.SelectImageType

internal object MainNavigation {
    const val route = "main"

    const val Home = this.route + "/home"
    const val StudyRooms = this.route + "/studyRooms"
    const val StudyRoomDetails = this.route + "/studyRoomDetails/{seatId}/{timeSlot}"
    const val NoticeDetails = this.route + "/noticeDetails/{noticeId}"
    const val RemainsApplication = this.route + "/remainsApplication"
    const val PointHistory = this.route + "/pointHistory"
    const val UploadProfileImage = this.route + "/uploadProfileImage"

    object EditPasswordNavigation {
        const val route = "editPassword"

        const val ConfirmPassword = this.route + "/confirmPassword"
        const val SetPassword = this.route + "/setPassword"
    }

    object Arguments {
        const val StudyRoomId = "study-room-id"
        const val Timeslot = "timeslot"
        const val NoticeId = "notice-id"
        const val SelectImageType = "select-image-type"
    }
}

fun NavGraphBuilder.mainNavigation(
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
        seatId: UUID,
        studyRoomAvailableTimeId: UUID,
    ) -> Unit,
    onNavigateToEditPasswordSetPassword: () -> Unit,
    onNavigateToHome: () -> Unit,
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
            )
        }
        composable(MainNavigation.StudyRooms) {
            StudyRoomsScreen(
                onPrevious = onPrevious,
                onNavigateToStudyRoomDetails = onNavigateToStudyRoomDetails,
            )
        }
        composable(
            route = MainNavigation.StudyRoomDetails,
            arguments = listOf(
                navArgument(MainNavigation.Arguments.StudyRoomId) {
                    type = NavType.UuidType
                },
                navArgument(MainNavigation.Arguments.Timeslot) {
                    type = NavType.UuidType
                },
            ),
        ) { backStackEntry ->
            backStackEntry.arguments?.run {
                val roomId = getString(MainNavigation.Arguments.StudyRoomId)
                val timeslot = getString(MainNavigation.Arguments.Timeslot)

                StudyRoomDetailsScreen(
                    onPrevious = onPrevious,
                    roomId = UUID.fromString(roomId),
                    timeSlot = UUID.fromString(timeslot),
                )
            }
        }
        composable(
            route = MainNavigation.NoticeDetails,
            arguments = listOf(
                navArgument(MainNavigation.Arguments.NoticeId) {
                    type = NavType.UuidType
                },
            ),
        ) { backStackEntry ->
            backStackEntry.arguments?.run {
                val noticeId = getString(MainNavigation.Arguments.NoticeId)
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
            route = MainNavigation.UploadProfileImage,
            arguments = listOf(navArgument(MainNavigation.Arguments.SelectImageType) {
                type = NavType.IntType
            }),
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.run {
                val selectImageType = getInt(MainNavigation.Arguments.SelectImageType)

                UploadProfileImageScreen(
                    selectImageType = SelectImageType.values()[selectImageType], // todo need to discuss
                    onPrevious = onPrevious,
                )
            }
        }
        editPasswordNavigation(
            onNavigateToEditPasswordSetPassword = onNavigateToEditPasswordSetPassword,
            onPrevious = onPrevious,
            onNavigateToHome = onNavigateToHome,
        )
    }
}

private fun NavGraphBuilder.editPasswordNavigation(
    onNavigateToEditPasswordSetPassword: () -> Unit,
    onPrevious: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    navigation(
        startDestination = MainNavigation.EditPasswordNavigation.ConfirmPassword,
        route = MainNavigation.EditPasswordNavigation.route,
    ) {
        composable(MainNavigation.EditPasswordNavigation.ConfirmPassword) {
            ConfirmPasswordScreen(
                onNavigateToEditPasswordSetPassword = onNavigateToEditPasswordSetPassword,
                onPrevious = onPrevious
            )
        }
        composable(MainNavigation.EditPasswordNavigation.SetPassword) {
            EditPasswordSetPasswordScreen(
                onNavigateToHome = onNavigateToHome,
            )
        }
    }
}
