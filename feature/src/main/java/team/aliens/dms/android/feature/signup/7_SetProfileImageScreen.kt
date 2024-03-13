package team.aliens.dms.android.feature.signup

// TODO: 구현

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@Destination
@Composable
internal fun SetProfileImageScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    // signUpViewModel: SignUpViewModel,
) {/*
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
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onSelectImageTypeDialogShow),
                    model = uiState.profileImageUri ?: defaultProfileUrl,
                    contentDescription = null,
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
    }*/
}