package team.aliens.dms_android.app.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms_android.feature.destinations.NoticeDetailsScreenDestination
import team.aliens.dms_android.feature.destinations.NotificationBoxScreenDestination
import team.aliens.dms_android.feature.destinations.PointHistoryScreenDestination
import team.aliens.dms_android.feature.destinations.RemainsApplicationScreenDestination
import team.aliens.dms_android.feature.destinations.StudyRoomDetailsScreenDestination
import team.aliens.dms_android.feature.destinations.StudyRoomListScreenDestination
import team.aliens.dms_android.feature.editpassword.navigation.EditPasswordNavGraph
import team.aliens.dms_android.feature.main.navigation.MainNavGraph

object AuthorizedNavGraph : NavGraphSpec {
    override val route: String = "authorized"
    override val startRoute: Route = MainNavGraph
    override val nestedNavGraphs: List<NavGraphSpec> =
        listOf(
            MainNavGraph,
            EditPasswordNavGraph,
        )

    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf<DestinationSpec<*>>(
            NoticeDetailsScreenDestination,
            NoticeDetailsScreenDestination,
            NotificationBoxScreenDestination,
            PointHistoryScreenDestination,
            RemainsApplicationScreenDestination,
            StudyRoomListScreenDestination,
            StudyRoomDetailsScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
