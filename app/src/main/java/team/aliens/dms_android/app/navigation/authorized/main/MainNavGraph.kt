package team.aliens.dms_android.app.navigation.authorized.main

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms_android.feature._feature.destinations.AnnouncementListScreenDestination
import team.aliens.dms_android.feature._feature.destinations.ApplicationScreenDestination
import team.aliens.dms_android.feature._feature.destinations.HomeScreenDestination
import team.aliens.dms_android.feature._feature.destinations.MyPageScreenDestination

object MainNavGraph : NavGraphSpec {
    override val route: String = "main"
    override val startRoute: Route = HomeScreenDestination
    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf<DestinationSpec<*>>(
            HomeScreenDestination,
            ApplicationScreenDestination,
            AnnouncementListScreenDestination,
            MyPageScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
