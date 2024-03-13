package team.aliens.dms.android.app.navigation.unauthorized

import team.aliens.dms.android.feature.findid.navigator.FindIdNavigator
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavigator
import team.aliens.dms.android.feature.signin.navigation.SignInNavigator
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

interface UnauthorizedNavigator :
    FindIdNavigator,
    ResetPasswordNavigator,
    SignInNavigator,
    SignUpNavigator
