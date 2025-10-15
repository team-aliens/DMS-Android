package team.aliens.dms.android.app.navigation.authorized

import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavigator
import team.aliens.dms.android.feature.editprofile.navigation.EditProfileNavigator
import team.aliens.dms.android.feature.main.navigation.MainNavigator
import team.aliens.dms.android.feature.notice.navigation.NoticeNavigator
import team.aliens.dms.android.feature.notification.navigation.NotificationBoxNavigator
import team.aliens.dms.android.feature.notification.navigation.NotificationSettingsNavigator
import team.aliens.dms.android.feature.outing.navigation.OutingNavigator
import team.aliens.dms.android.feature.point.navigation.PointHistoryNavigator
import team.aliens.dms.android.feature.remains.navigator.RemainsNavigator
import team.aliens.dms.android.feature.studyroom.navigation.StudyRoomNavigator
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator
import team.aliens.dms.android.feature.volunteers.navigation.VolunteersNavigator

interface AuthorizedNavigator :
    MainNavigator,
    EditPasswordNavigator,
    EditProfileNavigator,
    NoticeNavigator,
    NotificationBoxNavigator,
    NotificationSettingsNavigator,
    PointHistoryNavigator,
    RemainsNavigator,
    StudyRoomNavigator,
    OutingNavigator
