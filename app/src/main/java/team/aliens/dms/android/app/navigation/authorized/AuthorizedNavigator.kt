package team.aliens.dms.android.app.navigation.authorized

import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavigator
import team.aliens.dms.android.feature.editprofile.navigation.EditProfileNavigator
import team.aliens.dms.android.feature.main.navigation.MainNavigator
import team.aliens.dms.android.feature.notice.navigation.NoticeNavigator
import team.aliens.dms.android.feature.notification.navigation.NotificationNavigation
import team.aliens.dms.android.feature.point.navigation.PointHistoryNavigator
import team.aliens.dms.android.feature.remains.navigator.RemainsNavigator
import team.aliens.dms.android.feature.studyroom.navigation.StudyRoomNavigator

interface AuthorizedNavigator :
    MainNavigator,
    EditPasswordNavigator,
    EditProfileNavigator,
    NoticeNavigator,
    NotificationNavigation,
    PointHistoryNavigator,
    RemainsNavigator,
    StudyRoomNavigator
