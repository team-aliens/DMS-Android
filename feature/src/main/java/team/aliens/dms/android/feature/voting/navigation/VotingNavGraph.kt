package team.aliens.dms.android.feature.voting.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route

object VotingNavGraph : NavGraphSpec {
    override val route: String = "voting"
    override val startRoute: Route = VotingListScreenDestination
    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf(

        )
}
