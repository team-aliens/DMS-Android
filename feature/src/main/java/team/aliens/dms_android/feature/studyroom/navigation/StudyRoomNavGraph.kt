package team.aliens.dms_android.feature.studyroom.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms_android.feature.destinations.StudyRoomDetailsScreenDestination
import team.aliens.dms_android.feature.destinations.StudyRoomListScreenDestination

object StudyRoomNavGraph : NavGraphSpec {
    override val route: String = "study_room"
    override val startRoute: Route = StudyRoomListScreenDestination
    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf(
            StudyRoomListScreenDestination,
            StudyRoomDetailsScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
