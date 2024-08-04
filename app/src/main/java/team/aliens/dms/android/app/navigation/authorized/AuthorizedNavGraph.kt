package team.aliens.dms.android.app.navigation.authorized

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms.android.feature.destinations.AnnouncementListScreenDestination
import team.aliens.dms.android.feature.destinations.EditProfileImageScreenDestination
import team.aliens.dms.android.feature.destinations.MainDestination
import team.aliens.dms.android.feature.destinations.NoticeDetailsScreenDestination
import team.aliens.dms.android.feature.destinations.NotificationSettingsScreenDestination
import team.aliens.dms.android.feature.destinations.PointHistoryScreenDestination
import team.aliens.dms.android.feature.destinations.RemainsApplicationScreenDestination
import team.aliens.dms.android.feature.destinations.StudyRoomDetailsScreenDestination
import team.aliens.dms.android.feature.destinations.StudyRoomListScreenDestination
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavGraph
import team.aliens.dms.android.feature.outing.navigation.OutingNavGraph
import team.aliens.dms.android.feature.studyroom.navigation.StudyRoomNavGraph

object AuthorizedNavGraph : NavGraphSpec {
    override val route: String = "authorized"
    override val startRoute: Route = MainDestination routedIn this
    override val nestedNavGraphs: List<NavGraphSpec> =
        listOf(
            EditPasswordNavGraph,
            StudyRoomNavGraph,
            OutingNavGraph,
        )

    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf<DestinationSpec<*>>(
            MainDestination,
            EditProfileImageScreenDestination,
            AnnouncementListScreenDestination,
            RemainsApplicationScreenDestination,
            StudyRoomListScreenDestination,
            StudyRoomDetailsScreenDestination,
            NoticeDetailsScreenDestination,
            NotificationSettingsScreenDestination,
            PointHistoryScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
