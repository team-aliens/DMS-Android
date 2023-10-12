package team.aliens.dms_android.feature.resetpassword.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import team.aliens.dms_android.feature.destinations.AccountVerificationScreenDestination
import team.aliens.dms_android.feature.destinations.ResetPasswordEnterEmailVerificationCodeScreenDestination
import team.aliens.dms_android.feature.destinations.ResetPasswordSetPasswordScreenDestination

object ResetPasswordNavGraph : NavGraphSpec {
    override val route: String = "reset_password"
    override val startRoute: Route = AccountVerificationScreenDestination
    override val destinationsByRoute: Map<String, DestinationSpec<*>> =
        listOf(
            AccountVerificationScreenDestination,
            ResetPasswordEnterEmailVerificationCodeScreenDestination,
            ResetPasswordSetPasswordScreenDestination,
        )
            .routedIn(navGraphSpec = this)
            .associateBy { it.route }
}
