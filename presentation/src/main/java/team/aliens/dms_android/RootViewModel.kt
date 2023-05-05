package team.aliens.dms_android

import team.aliens.dms_android._base.BaseUiState
import team.aliens.dms_android._base.BaseViewModel

internal class RootViewModel(

) : BaseViewModel<RootState> {
}

private data class RootState(
    val route: String = "signIn",
) : BaseUiState
