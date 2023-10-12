package team.aliens.dms_android.app.navigation

import team.aliens.dms_android.app.navigation.authorized.AuthorizedNavigator
import team.aliens.dms_android.app.navigation.unauthorized.UnauthorizedNavigator
import javax.inject.Inject

interface DmsNavigator : AuthorizedNavigator, UnauthorizedNavigator

class DmsNavigatorImpl @Inject constructor() : DmsNavigator
