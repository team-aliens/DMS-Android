package team.aliens.dms.android.feature.signup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.skydoves.landscapist.glide.GlideImage
import team.aliens.dms.android.design_system.button.DormButtonColor
import team.aliens.dms.android.design_system.button.DormContainedLargeButton
import team.aliens.dms.android.design_system.extension.RatioSpace
import team.aliens.dms.android.design_system.extension.Space
import team.aliens.dms.android.design_system.modifier.dormClickable
import team.aliens.dms.android.design_system.theme.DormTheme
import team.aliens.dms.android.design_system.toast.LocalToast
import team.aliens.dms.android.design_system.typography.Body2
import team.aliens.dms.android.design_system.typography.ButtonText
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature._legacy.AppLogo
import team.aliens.dms.android.feature.editprofile.dialog.SelectImageTypeDialog
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@Destination
@Composable
internal fun SetProfileImageScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    signUpViewModel: SignUpViewModel,
) {
    val selectImageFromGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) {
        if (it != null) {
            signUpViewModel.postIntent(SignUpIntent.SetProfileImage.SelectProfileImage(it))
        }
    }

    val onSelectPhoto = {
        selectImageFromGalleryLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    val toast = LocalToast.current
    val uploadImageFailedMessage =
        stringResource(id = R.string.sign_up_profile_error_load_image_error)
    val uploadImageNotSelectedMessage =
        stringResource(id = R.string.sign_up_profile_error_image_not_selected)

    var selectImageTypeDialogState by remember { mutableStateOf(false) }
    val onSelectImageTypeDialogShow = { selectImageTypeDialogState = true }
    val onSelectImageTypeDialogDismiss = { selectImageTypeDialogState = false }

    val uiState by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        signUpViewModel.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.SetProfileImage.UploadImageSuccess -> {
                    navigator.openTerms()
                }

                is SignUpSideEffect.SetProfileImage.UploadImageFailed -> {
                    toast.showErrorToast(uploadImageFailedMessage)
                }

                is SignUpSideEffect.SetProfileImage.UploadImageNotSelected -> {
                    toast.showErrorToast(uploadImageNotSelectedMessage)
                }

                else -> {}
            }
        }
    }

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
            .background(DormTheme.colors.surface)
            .padding(
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        Spacer(modifier = Modifier.height(108.dp))
        AppLogo(darkIcon = isSystemInDarkTheme())
        Space(space = 8.dp)
        Body2(text = stringResource(id = R.string.ProfileImage))
        Space(space = 80.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.size(150.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onSelectImageTypeDialogShow),
                    imageModel = { uiState.profileImageUri ?: defaultProfileUrl },
                )
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.addplusimage),
                    contentDescription = null,
                )
            }
            RatioSpace(height = 0.62f)
            ButtonText(
                modifier = Modifier.dormClickable(
                    rippleEnabled = false,
                    onClick = navigator::openTerms,
                ),
                text = stringResource(id = R.string.SettingLater),
            )
            Space(space = 30.dp)
            DormContainedLargeButton(
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
                enabled = uiState.profileImageButtonEnabled,
            ) {
                signUpViewModel.postIntent(SignUpIntent.SetProfileImage.UploadImage)
            }
        }
    }
}