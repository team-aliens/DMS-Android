package team.aliens.dms_android.feature.image

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.toast.rememberToast
import team.aliens.dms_android.util.SelectImageType
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.util.fetchImage
import team.aliens.dms_android.viewmodel.image.ConfirmImageViewModel
import team.aliens.presentation.R

@Composable
internal fun ConfirmImageScreen(
    selectImageType: Int,
    navController: NavController,
    confirmImageViewModel: ConfirmImageViewModel = hiltViewModel(),
) {

    val toast = rememberToast()

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    var isSignUp by remember { mutableStateOf(false) }

    isSignUp = navController.previousBackStackEntry?.arguments?.getBoolean("isSignUp") ?: false

    LaunchedEffect(Unit) {
        confirmImageViewModel.confirmImageEvent.collect {
            when (it) {
                ConfirmImageViewModel.Event.ProfileEdited -> {
                    navController.popBackStack()
                }
                else -> {
                    toast(
                        getStringFromEvent(
                            context = context,
                            event = it,
                        ),
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        when (selectImageType) {
            SelectImageType.TAKE_PHOTO.ordinal -> {
                // take Photo
            }
            SelectImageType.SELECT_FROM_GALLERY.ordinal -> {

                scope.launch {

                    val selectedImage = fetchImage(context) ?: return@launch

                    confirmImageViewModel.setImage(selectedImage)
                }
            }
        }
    }


    var gettingImageOptionDialogState by remember {
        mutableStateOf(false)
    }

    if (gettingImageOptionDialogState) {
        GettingImageOptionDialog(
            onCancel = {
                gettingImageOptionDialogState = false
            },
            onTakePhoto = {

            },
            onSelectPhoto = {
                scope.launch {

                    val selectedImage = fetchImage(context) ?: return@launch

                    confirmImageViewModel.setImage(selectedImage)

                    gettingImageOptionDialogState = false
                }
            },
        )
    }

    val confirmImageState = confirmImageViewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        TopBar(
            title = if (isSignUp) stringResource(id = R.string.SetProfile)
            else stringResource(R.string.EditProfile),
        ) {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DormColor.Gray200)
                .padding(
                    horizontal = 16.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Box(
                contentAlignment = Alignment.BottomEnd,
            ) {

                // `사용자` 프로필
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .dormClickable {
                            gettingImageOptionDialogState = true
                        },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(
                        model = confirmImageState.selectedImage?.toUri(),
                    ),
                )

                // 수정 아이콘
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(
                        id = if (isSignUp) R.drawable.addplusimage
                        else R . drawable . ic_mypage_edit,
                ),
                contentDescription = null,
                )
            }


            Spacer(
                modifier = Modifier.height(80.dp),
            )


            // 확인 버튼
            DormContainedLargeButton(
                text = stringResource(R.string.Check),
                color = DormButtonColor.Blue,
            ) {
                confirmImageViewModel.editProfileImage()
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: ConfirmImageViewModel.Event,
): String {
    return context.getString(
        when (event) {
            ConfirmImageViewModel.Event.BadRequestException -> R.string.BadRequest
            ConfirmImageViewModel.Event.UnAuthorizedTokenException -> R.string.UnAuthorized
            ConfirmImageViewModel.Event.CannotConnectException -> R.string.NoInternetException
            ConfirmImageViewModel.Event.TooManyRequestException -> R.string.TooManyRequest
            ConfirmImageViewModel.Event.InternalServerException -> R.string.ServerException
            ConfirmImageViewModel.Event.UnknownException -> R.string.UnKnownException
            else -> throw IllegalArgumentException()
        },
    )
}