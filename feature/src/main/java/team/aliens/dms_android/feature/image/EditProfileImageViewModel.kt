package team.aliens.dms_android.feature.image

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms_android.feature._legacy.base.MviIntent
import team.aliens.dms_android.feature._legacy.base.MviSideEffect
import team.aliens.dms_android.feature._legacy.base.MviState
import team.aliens.dms_android.domain.model.file.UploadFileInput
import team.aliens.dms_android.domain.model.student.EditProfileInput
import team.aliens.dms_android.domain.usecase.file.UploadFileUseCase
import team.aliens.dms_android.domain.usecase.student.EditProfileUseCase
import javax.inject.Inject

@HiltViewModel
internal class EditProfileImageViewModel @Inject constructor(
    private val uploadFileUseCase: UploadFileUseCase,
    private val editProfileUseCase: EditProfileUseCase,
) : BaseMviViewModel<UploadProfileImageIntent, UploadProfileImageState, UploadProfileImageSideEffect>(
    initialState = UploadProfileImageState.initial(),
) {
    override fun processIntent(intent: UploadProfileImageIntent) {
        when (intent) {
            is UploadProfileImageIntent.SelectImage -> reduce(
                newState = stateFlow.value.copy(
                    selectedImageUri = intent.selectedImageUri,
                ),
            )

            UploadProfileImageIntent.UploadAndEditProfile -> uploadAndEditProfile()
        }
    }

    private fun uploadAndEditProfile() {
        if (stateFlow.value.selectedImageUri == null) {
            postSideEffect(UploadProfileImageSideEffect.ImageNotSelected)
            return
        }
        disableUploadButton()
        viewModelScope.launch(Dispatchers.IO) {
            val result = kotlin.runCatching {
                uploadProfile()
            }.onFailure {
                postSideEffect(UploadProfileImageSideEffect.UploadProfileImageFailed)
                return@launch
            }

            if (result.isSuccess) kotlin.runCatching {
                editProfileUseCase(
                    editProfileInput = EditProfileInput(
                        profileImageUrl = result.getOrThrow(),
                    ),
                )
            }.onSuccess {
                postSideEffect(UploadProfileImageSideEffect.EditProfileSucceed)
            }.onFailure {
                postSideEffect(UploadProfileImageSideEffect.EditProfileFailed)
            }
        }
    }

    private fun uploadProfile(): String {
        return runBlocking(Dispatchers.IO) {
            uploadFileUseCase(
                uploadFileInput = UploadFileInput(
                    file = stateFlow.value.selectedImageUri!!.toFile(), // not-null asserted
                ),
            )
        }.fileUrl
    }

    private fun disableUploadButton() {
        reduce(
            newState = stateFlow.value.copy(
                uploadButtonEnabled = false,
            )
        )
    }
}

internal sealed class UploadProfileImageIntent : MviIntent {
    class SelectImage(val selectedImageUri: Uri) : UploadProfileImageIntent()
    object UploadAndEditProfile : UploadProfileImageIntent()
}

internal data class UploadProfileImageState(
    val selectedImageUri: Uri?,
    val uploadButtonEnabled: Boolean,
) : MviState {
    companion object {
        fun initial() = UploadProfileImageState(
            selectedImageUri = null,
            uploadButtonEnabled = false,
        )
    }
}

internal sealed class UploadProfileImageSideEffect : MviSideEffect {
    object EditProfileSucceed : UploadProfileImageSideEffect()
    object EditProfileFailed : UploadProfileImageSideEffect()
    object ImageNotSelected : UploadProfileImageSideEffect()
    object UploadProfileImageFailed : UploadProfileImageSideEffect()
}
