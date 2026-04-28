package team.aliens.dms.android.feature.profile.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.profile.viewmodel.SelectProfileSideEffect
import team.aliens.dms.android.feature.profile.viewmodel.SelectProfileViewModel

@Composable
internal fun SelectProfile(
    onBack: () -> Unit,
    onNavigateAdjustProfile: (String) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: SelectProfileViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val updatedOnNavigateAdjustProfile by rememberUpdatedState(onNavigateAdjustProfile)
    val updatedOnShowSnackBar by rememberUpdatedState(onShowSnackBar)

    val updatedOnBack by rememberUpdatedState(onBack)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        if (uri != null) {
            viewModel.selectImage(uri.toString())
        } else if (state.selectedUri.isBlank()) {
            updatedOnBack()
        }
    }

    LaunchedEffect(Unit) {
        photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is SelectProfileSideEffect.SuccessProfileImage -> updatedOnNavigateAdjustProfile(it.profileImageUrl)
                is SelectProfileSideEffect.ProfileImageBadRequest -> updatedOnShowSnackBar(DmsSnackBarType.SUCCESS, "업로드 성공!")
                is SelectProfileSideEffect.FailProfileImage -> updatedOnShowSnackBar(DmsSnackBarType.ERROR, "이미지 업로드에 실패했어요")
                is SelectProfileSideEffect.FailFetchPresignedUrl -> updatedOnShowSnackBar(DmsSnackBarType.ERROR, it.message)
            }
        }
    }

    SelectProfileScreen(
        onBack = onBack,
        uploadProfileImage = viewModel::uploadProfileImage,
        selectedUri = state.selectedUri,
        enabled = state.enabled,
        onPickImage = {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
    )
}

@Composable
private fun SelectProfileScreen(
    onBack: () -> Unit,
    uploadProfileImage: (Uri?) -> Unit,
    selectedUri: String,
    enabled: Boolean,
    onPickImage: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DmsTopAppBar(
            onBackClick = onBack,
            actions = {
                DmsButton(
                    onClick = { uploadProfileImage(selectedUri.toUri()) },
                    text = "선택",
                    buttonType = ButtonType.Text,
                    buttonColor = ButtonColor.Primary,
                    enabled = enabled,
                )
            },
        )
        Spacer(modifier = Modifier.weight(1f))
        ProfileImagePicker(
            selectedUri = selectedUri,
            onPickImage = onPickImage,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ProfileImagePicker(
    selectedUri: String,
    onPickImage: () -> Unit,
) {
    val hasImage = selectedUri.isNotEmpty()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(DmsTheme.colorScheme.surfaceVariant)
                .border(2.dp, DmsTheme.colorScheme.primary, CircleShape)
                .clickable(onClick = onPickImage),
            contentAlignment = Alignment.Center,
        ) {
            if (hasImage) {
                AsyncImage(
                    model = selectedUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize(),
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.AddAPhoto,
                    contentDescription = "사진 선택",
                    tint = DmsTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "탭하여 사진 선택",
            style = DmsTheme.typography.labelM,
            color = DmsTheme.colorScheme.inverseOnSurface,
        )
    }
}
