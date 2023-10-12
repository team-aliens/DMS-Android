package team.aliens.dms_android.app.navigation.authorized

import team.aliens.dms_android.feature.editpassword.navigation.EditPasswordNavigator
import team.aliens.dms_android.feature.editprofile.navigation.EditProfileNavigator
import team.aliens.dms_android.feature.main.navigation.MainNavigator

interface AuthorizedNavigator :
    EditPasswordNavigator,
    EditProfileNavigator,
    MainNavigator
