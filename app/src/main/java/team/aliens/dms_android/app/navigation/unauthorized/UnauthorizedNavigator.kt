package team.aliens.dms_android.app.navigation.unauthorized

import team.aliens.dms_android.feature.findid.navigator.FindIdNavigator
import team.aliens.dms_android.feature.resetpassword.navigation.ResetPasswordNavigator
import team.aliens.dms_android.feature.signin.navigation.SignInNavigator

interface UnauthorizedNavigator :
    FindIdNavigator,
    ResetPasswordNavigator,
    SignInNavigator
