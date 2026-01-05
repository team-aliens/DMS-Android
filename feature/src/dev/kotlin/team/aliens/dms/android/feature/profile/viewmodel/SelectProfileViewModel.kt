package team.aliens.dms.android.feature.profile.viewmodel

import android.R
import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import javax.inject.Inject

@HiltViewModel
internal class SelectProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
): BaseStateViewModel<SelectProfileState, Unit>(SelectProfileState()) {

    init {
        getUriImage()
    }

    private fun getUriImage() {
        val uriList = mutableListOf<String>()
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID)


        // contentResolver를 사용하여 외부 저장소에서 이미지 데이터 쿼리
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
                setState { it.copy(uriList = uriList) }
            }
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
}

data class SelectProfileState(
    val selectedUri: String = "",
    val enabled: Boolean = false,
    val uriList: List<String> = emptyList(),
)
