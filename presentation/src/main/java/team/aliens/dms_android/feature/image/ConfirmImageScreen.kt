package team.aliens.dms_android.feature.image

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import team.aliens.dms_android.util.SelectImageType
import team.aliens.dms_android.util.fetchImage
import team.aliens.dms_android.viewmodel.image.ConfirmImageViewModel
import java.io.File

@Composable
internal fun ConfirmImageScreen(
    selectImageType: Int,
    navController: NavController,
    confirmImageViewModel: ConfirmImageViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        when (selectImageType) {
            SelectImageType.TAKE_PHOTO.ordinal -> {
                // take Photo
            }
            SelectImageType.SELECT_FROM_GALLERY.ordinal -> {

                scope.launch {

                    val selectedImage = getImageFromGallery(context)

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

                    val selectedImage = getImageFromGallery(context)

                    confirmImageViewModel.setImage(selectedImage)

                    gettingImageOptionDialogState = false
                }
            },
        )
    }

    val confirmImageState = confirmImageViewModel.state.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
        ) {

            // 사용자 프로필
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable {
                        gettingImageOptionDialogState = true
                    },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(
                    model = confirmImageState.selectedImage?.toUri(),
                ),
            )

            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(
                    id = team.aliens.presentation.R.drawable.ic_mypage_edit,
                ),
                contentDescription = null,
            )
        }
    }
}

private suspend fun getImageFromGallery(
    context: Context,
): File {

    val selectedImage = fetchImage(context)

    checkNotNull(selectedImage)

    return selectedImage
}
