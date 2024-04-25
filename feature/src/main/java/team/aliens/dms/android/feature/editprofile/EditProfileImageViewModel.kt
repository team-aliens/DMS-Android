package team.aliens.dms.android.feature.editprofile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.file.repository.FileRepository
import team.aliens.dms.android.data.student.repository.StudentRepository
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class EditProfileImageViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val fileRepository: FileRepository,
) : BaseMviViewModel<EditProfileImageUiState, EditProfileImageIntent, EditProfileImageSideEffect>(
    initialState = EditProfileImageUiState.initial(),
) {
    override fun processIntent(intent: EditProfileImageIntent) {
        when (intent) {
            is EditProfileImageIntent.UpdateProfileImage -> updateProfileImage(
                context = intent.context,
                uri = intent.uri,
            )

            is EditProfileImageIntent.EditProfile -> editProfile()
        }
    }

    private fun updateProfileImage(
        context: Context,
        uri: Uri?,
    ) {
        if (uri != null) {
            reduce(newState = stateFlow.value.copy(uri = uri))
            fetchPresignedUrl(
                file = team.aliens.dms.android.core.file.File.toFile(
                    context = context,
                    uri = uri,
                )
            )
        } else {
            postSideEffect(EditProfileImageSideEffect.ProfileImageBadRequest)
        }
    }

    private fun fetchPresignedUrl(
        file: File,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                fileRepository.fetchPresignedUrl(fileName = file.name)
            }.onSuccess { fileUrl ->
                runCatching {
                    fileRepository.uploadFile(
                        presignedUrl = fileUrl.fileUploadUrl,
                        file = file,
                    )
                }.onSuccess {
                    reduce(
                        newState = stateFlow.value.copy(
                            profileImageUrl = fileUrl.fileUrl,
                            buttonEnabled = true,
                        )
                    )
                }
            }.onFailure {
                postSideEffect(EditProfileImageSideEffect.ProfileImageBadRequest)
            }
        }
    }

    private fun editProfile() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            studentRepository.editProfile(stateFlow.value.profileImageUrl!!)
        }.onSuccess {
            postSideEffect(EditProfileImageSideEffect.ProfileImageSet)
            reduce(newState = stateFlow.value.copy(buttonEnabled = false))
        }.onFailure {
            postSideEffect(EditProfileImageSideEffect.ProfileImageBadRequest)
        }
    }

}

data class EditProfileImageUiState(
    val profileImageUrl: String?,
    val uri: Uri?,
    val buttonEnabled: Boolean,
) : UiState() {
    companion object {
        fun initial() = EditProfileImageUiState(
            profileImageUrl = null,
            uri = null,
            buttonEnabled = false,
        )
    }
}

internal sealed class EditProfileImageIntent : Intent() {
    class UpdateProfileImage(val uri: Uri?, val context: Context) : EditProfileImageIntent()
    data object EditProfile : EditProfileImageIntent()
}

sealed class EditProfileImageSideEffect : SideEffect() {
    data object ProfileImageSet : EditProfileImageSideEffect()
    data object ProfileImageBadRequest : EditProfileImageSideEffect()
}
