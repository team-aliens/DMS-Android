package team.aliens.dms.android.feature.profile.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.file.repository.FileRepository
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class SelectProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fileRepository: FileRepository,
) : BaseStateViewModel<SelectProfileState, SelectProfileSideEffect>(SelectProfileState()) {

    internal fun selectImage(uri: String) {
        setState { it.copy(selectedUri = uri, enabled = uri.isNotBlank()) }
    }

    internal fun uploadProfileImage(uri: Uri?) {
        if (uri != null) {
            setState { it.copy(uri = uri) }
            fetchPresignedUrl(
                file = team.aliens.dms.android.core.file.File.toFile(
                    context = context,
                    uri = uri,
                ),
            )
        } else {
            sendEffect(SelectProfileSideEffect.ProfileImageBadRequest)
        }
    }

    private fun fetchPresignedUrl(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            fileRepository.fetchPresignedUrl(file.name).fold(
                onSuccess = { presignedInfo ->
                    fileRepository.uploadFile(
                        presignedUrl = presignedInfo.fileUploadUrl,
                        file = file,
                    ).onSuccess { fileUrl ->
                        setState {
                            it.copy(
                                profileImageUrl = fileUrl.fileUrl,
                                buttonEnabled = true,
                            )
                        }
                        sendEffect(SelectProfileSideEffect.SuccessProfileImage(fileUrl.fileUrl))
                    }.onFailure {
                        sendEffect(SelectProfileSideEffect.FailProfileImage)
                    }
                },
                onFailure = {
                    sendEffect(SelectProfileSideEffect.FailFetchPresignedUrl("프로필 이미지 업로드에 실패했습니다"))
                },
            )
        }
    }
}

data class SelectProfileState(
    val selectedUri: String = "",
    val enabled: Boolean = false,
    val profileImageUrl: String? = null,
    val uri: Uri? = null,
    val buttonEnabled: Boolean = false,
)

sealed class SelectProfileSideEffect {
    data class SuccessProfileImage(val profileImageUrl: String) : SelectProfileSideEffect()
    data object ProfileImageBadRequest : SelectProfileSideEffect()
    data object FailProfileImage : SelectProfileSideEffect()
    data class FailFetchPresignedUrl(val message: String) : SelectProfileSideEffect()
}