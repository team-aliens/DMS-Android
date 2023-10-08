package team.aliens.dms_android.feature.feature.main.image

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.skydoves.landscapist.glide.GlideImage
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.dms_android.feature.extension.collectInLaunchedEffectWithLifeCycle
import team.aliens.dms_android.presentation.R
import team.aliens.dms_android.feature.util.SelectImageType
import team.aliens.dms_android.feature.util.TopBar

@Destination
@Composable
internal fun EditProfileImageScreen(
    editProfileImageViewModel: EditProfileImageViewModel = hiltViewModel(),
    selectImageType: SelectImageType? = null,
    onPrevious: () -> Unit,
) {
    val uiState by editProfileImageViewModel.stateFlow.collectAsStateWithLifecycle()
    editProfileImageViewModel.sideEffectFlow.collectInLaunchedEffectWithLifeCycle { sideEffect ->
        when (sideEffect) {
            UploadProfileImageSideEffect.EditProfileFailed -> {}
            UploadProfileImageSideEffect.EditProfileSucceed -> onPrevious()
            UploadProfileImageSideEffect.ImageNotSelected -> {}
            UploadProfileImageSideEffect.UploadProfileImageFailed -> {}
        }
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { takenImage: Uri? ->
        if (takenImage != null) {
            editProfileImageViewModel.postIntent(
                UploadProfileImageIntent.SelectImage(takenImage),
            )
        }
    }
    val selectImageFromGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { selectedImage: Uri? ->
        if (selectedImage != null) {
            editProfileImageViewModel.postIntent(
                UploadProfileImageIntent.SelectImage(selectedImage),
            )
        }
    }
    // todo
    val onTakePhoto = {
        /*takePhotoLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.)
        )*/
    }
    val onSelectPhoto = {
        selectImageFromGalleryLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
        )
    }
    LaunchedEffect(Unit) {
        if (selectImageType != null) when (selectImageType) {
            SelectImageType.TAKE_PHOTO -> {
                // TODO take Photo
            }

            SelectImageType.SELECT_FROM_GALLERY -> onSelectPhoto()
        }
    }

    var selectImageTypeDialogState by remember { mutableStateOf(false) }
    val onSelectImageTypeDialogShow = { selectImageTypeDialogState = true }
    val onSelectImageTypeDialogDismiss = { selectImageTypeDialogState = false }

    if (selectImageTypeDialogState) {
        SelectImageTypeDialog(
            onCancel = onSelectImageTypeDialogDismiss,
            onTakePhoto = {},
            onSelectPhoto = onSelectPhoto,
            onDialogDismiss = onSelectImageTypeDialogDismiss,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(
            title = stringResource(R.string.my_page_edit_profile),
            onPrevious = onPrevious,
        )
        Spacer(Modifier.weight(1f))
        Box(
            contentAlignment = Alignment.BottomEnd,
        ) {
            GlideImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .dormClickable { onSelectImageTypeDialogShow() },
                imageModel = { uiState.selectedImageUri },
            )
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.ic_mypage_edit),
                contentDescription = null,
            )
        }
        Spacer(Modifier.height(80.dp))
        DormContainedLargeButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.Check),
            color = DormButtonColor.Blue,
            enabled = uiState.uploadButtonEnabled,
        ) {
            editProfileImageViewModel.postIntent(UploadProfileImageIntent.UploadAndEditProfile)
        }
        Spacer(Modifier.weight(1f))
    }
}
