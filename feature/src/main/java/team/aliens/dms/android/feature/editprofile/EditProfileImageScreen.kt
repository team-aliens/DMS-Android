package team.aliens.dms.android.feature.editprofile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.editprofile.navigation.EditProfileNavigator

@Destination
@Composable
internal fun EditProfileImageScreen(
    modifier: Modifier = Modifier,
    navigator: EditProfileNavigator,
    // editProfileImageViewModel: EditProfileImageViewModel = hiltViewModel(),
) {/*
    val uiState by editProfileImageViewModel.stateFlow.collectAsStateWithLifecycle()
    editProfileImageViewModel.sideEffectFlow.collectInLaunchedEffectWithLifeCycle { sideEffect ->
        when (sideEffect) {
            UploadProfileImageSideEffect.EditProfileFailed -> {}
            UploadProfileImageSideEffect.EditProfileSucceed -> navigator.popBackStack()
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
        *//*takePhotoLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.)
        )*//*
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
            else -> {}
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
        modifier = modifier
            .fillMaxSize()
            .background(DormTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(
            title = stringResource(R.string.my_page_edit_profile),
            onPrevious = navigator::popBackStack,
        )
        Spacer(Modifier.weight(1f))
        Box(
            contentAlignment = Alignment.BottomEnd,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .dormClickable { onSelectImageTypeDialogShow() },
                model = uiState.selectedImageUri,
                contentDescription = null,
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
    }*/
}
