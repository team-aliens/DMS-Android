package team.aliens.dms_android.feature.signup.ui.last

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.extension.RatioSpace
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.main.image.SelectImageTypeDialog
import team.aliens.dms_android.feature.signup.SignUpIntent
import team.aliens.dms_android.feature.signup.SignUpViewModel
import team.aliens.presentation.R

private const val defaultProfileUrl =
    "https://image-dms.s3.ap-northeast-2.amazonaws.com/59fd0067-93ef-4bcb-8722-5bc8786c5156%7C%7C%E1%84%83%E1%85%A1%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%85%E1%85%A9%E1%84%83%E1%85%B3.png"

@Composable
internal fun SetProfileImageScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel,
) {
    val selectImageFromGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ){
        if(it != null){
            signUpViewModel.postIntent(SignUpIntent.SetProfileImage.SelectProfileImage(it))
        }
    }

    val onSelectPhoto = {
        selectImageFromGalleryLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    var selectImageTypeDialogState by remember { mutableStateOf(false) }
    val onSelectImageTypeDialogShow = { selectImageTypeDialogState = true }
    val onSelectImageTypeDialogDismiss = { selectImageTypeDialogState = false }

    val state by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

    if (selectImageTypeDialogState) {
        SelectImageTypeDialog(
            onCancel = onSelectImageTypeDialogDismiss,
            onTakePhoto = {},
            onSelectPhoto = onSelectPhoto,
            onDialogDismiss = {},
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
                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onSelectImageTypeDialogShow),
                    painter = rememberAsyncImagePainter(state.profileImageUri ?: defaultProfileUrl),
                    contentDescription = stringResource(R.string.profile_image),
                    contentScale = ContentScale.Crop,
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
                ) {

                },
                text = stringResource(id = R.string.SettingLater),
            )
            Space(space = 30.dp)
            DormContainedLargeButton(
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
                enabled = state.confirmProfileImageButtonEnabled,
            ) {
                signUpViewModel.postIntent(SignUpIntent.SetProfileImage.UploadImage)
            }
        }
    }
}