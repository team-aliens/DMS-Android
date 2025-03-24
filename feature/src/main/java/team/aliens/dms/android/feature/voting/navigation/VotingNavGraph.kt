package team.aliens.dms.android.feature.voting.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms.android.feature.destinations.VotingScreenDestination

object VotingNavGraph : NavGraphSpec {
    override val route: String = "voting"
    override val startRoute: Route = VotingScreenDestination routedIn this
    override val destinationsByRoute: Map<String, DestinationSpec<*>> = listOf<DestinationSpec<*>>(
        VotingScreenDestination,
        VotingModelStudentScreenDestination,
        VotingStudentScreenDestination,
        VotingSelectedScreenDestination,
        VotingApprovalScreenDestination,
    )
        .routedIn(navGraphSpec = this)
        .associateBy { it.route }
}
