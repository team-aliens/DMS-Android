package team.aliens.dms_android.feature.editpassword.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms_android.feature.destinations.ConfirmPasswordScreenDestination
import team.aliens.dms_android.feature.destinations.EditPasswordSetPasswordScreenDestination

object EditPasswordNavGraph : NavGraphSpec {
    override val route: String = "edit_password"
    override val startRoute: Route = ConfirmPasswordScreenDestination routedIn this
    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf(
            ConfirmPasswordScreenDestination,
            EditPasswordSetPasswordScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
