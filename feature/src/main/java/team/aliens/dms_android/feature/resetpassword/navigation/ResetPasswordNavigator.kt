package team.aliens.dms_android.feature.resetpassword.navigation

interface ResetPasswordNavigator {
    fun openResetPasswordEnterEmailVerificationCode()
    fun openResetPasswordSetPassword()
    fun openSignIn()

    fun popBackStack()
}
