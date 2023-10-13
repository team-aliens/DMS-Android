package team.aliens.dms_android.app.navigation.authorized

import team.aliens.dms_android.feature.editpassword.navigation.EditPasswordNavigator
import team.aliens.dms_android.feature.editprofile.navigation.EditProfileNavigator
import team.aliens.dms_android.feature.main.navigation.MainNavigator
import team.aliens.dms_android.feature.notice.navigation.NoticeNavigator
import team.aliens.dms_android.feature.notification.navigation.NotificationNavigation
import team.aliens.dms_android.feature.point.navigation.PointHistoryNavigator
import team.aliens.dms_android.feature.remains.navigator.RemainsNavigator
import team.aliens.dms_android.feature.studyroom.navigation.StudyRoomNavigator

interface AuthorizedNavigator :
    EditPasswordNavigator,
    EditProfileNavigator,
    MainNavigator,
    NoticeNavigator,
    NotificationNavigation,
    PointHistoryNavigator,
    RemainsNavigator,
    StudyRoomNavigator