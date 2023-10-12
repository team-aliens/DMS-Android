package team.aliens.dms_android.app.navigation.authorized

import team.aliens.dms_android.feature.editpassword.navigation.EditPasswordNavigator
import team.aliens.dms_android.feature.editprofile.navigation.EditProfileNavigator
import team.aliens.dms_android.feature.main.navigation.MainNavigator
import team.aliens.dms_android.feature.notice.navigation.NoticeNavigator
import team.aliens.dms_android.feature.notificationbox.navigation.NotificationBoxNavigator

interface AuthorizedNavigator :
    EditPasswordNavigator,
    EditProfileNavigator,
    MainNavigator,
    NoticeNavigator,
    NotificationBoxNavigator
