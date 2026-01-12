package team.aliens.dms.android.feature.profile.viewmodel

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
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
): BaseStateViewModel<SelectProfileState, SelectProfileSideEffect>(SelectProfileState()) {

    internal fun loadImagesIfNeeded() {
        if (uiState.value.uriList.isNotEmpty()) return
        getUriImage()
    }

    internal fun getUriImage() {
        viewModelScope.launch(Dispatchers.IO) {
            val uriList = mutableListOf<String>()
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.Media._ID)

            val cursor = context.contentResolver.query(
                contentUri,
                projection,
                null,
                null,
                "${MediaStore.Images.Media.DATE_ADDED} DESC"
            )

            cursor?.use {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val imageUri = ContentUris.withAppendedId(contentUri, id)
                    uriList.add(imageUri.toString())
                }
            }
            setState { it.copy(uriList = uriList) }
        }
    }

    internal fun selectImage(uri: String) {
        with(uiState.value) {
            val isSelected = selectedUri == uri && selectedUri.isNotBlank()

            val newSelectedUri = if (isSelected) {
                ""
            } else {
                uri
            }

            setState { it.copy(selectedUri = newSelectedUri, enabled = !isSelected) }
        }
    }

    internal fun uploadProfileImage(
        uri: Uri?,
    ) {
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

    internal fun fetchPresignedUrl(
        file: File,
    ) {
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
                        sendEffect(SelectProfileSideEffect.SuccessProfileImage(fileUrl?.fileUrl.toString()))
                    }.onFailure {
                        sendEffect(SelectProfileSideEffect.FailProfileImage)
                    }
                },
                onFailure = {

                }
            )
        }
    }
}

data class SelectProfileState(
    val selectedUri: String = "",
    val enabled: Boolean = false,
    val uriList: List<String> = emptyList(),
    val profileImageUrl: String? = null,
    val uri: Uri? = null,
    val buttonEnabled: Boolean = false,
)

sealed class SelectProfileSideEffect {
    data class SuccessProfileImage(val profileImageUrl: String) : SelectProfileSideEffect()
    data object ProfileImageBadRequest : SelectProfileSideEffect()

    data object FailProfileImage : SelectProfileSideEffect()
}
