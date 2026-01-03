package team.aliens.dms.android.feature.resetpassword.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.dms.android.feature.resetpassword.viewmodel.CheckPasswordViewModel

@Composable
internal fun ResetPassword() {
    val viewModel: CheckPasswordViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ResetPasswordScreen(

    )
}

@Composable
private fun ResetPasswordScreen(

) {
}
