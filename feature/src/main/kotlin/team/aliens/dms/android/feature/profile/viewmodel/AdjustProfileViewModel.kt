package team.aliens.dms.android.feature.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.student.repository.StudentRepository
import javax.inject.Inject

@HiltViewModel
internal class AdjustProfileViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
): BaseStateViewModel<AdjustProfileState, AdjustProfileSideEffect>(AdjustProfileState()) {

    internal fun editProfile(profileImageUrl: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            studentRepository.editProfile(profileImageUrl)
        }.onSuccess {
            sendEffect(AdjustProfileSideEffect.ProfileImageSet)
            setState { it.copy(buttonEnabled = false) }
        }.onFailure {
            sendEffect(AdjustProfileSideEffect.ProfileImageBadRequest)
        }
    }
}

data class AdjustProfileState(
    val profileImageUrl: String? = null,
    val uri: Uri? = null,
    val buttonEnabled: Boolean = false,
)

sealed class AdjustProfileSideEffect {
    data object ProfileImageSet : AdjustProfileSideEffect()
    data object ProfileImageBadRequest : AdjustProfileSideEffect()
}
