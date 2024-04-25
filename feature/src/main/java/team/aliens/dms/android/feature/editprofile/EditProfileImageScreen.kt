package team.aliens.dms.android.feature.editprofile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.Button
import team.aliens.dms.android.core.designsystem.DmsIcon
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.editprofile.navigation.EditProfileNavigator
import team.aliens.dms.android.feature.signup.SignUpUiState

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun EditProfileImageScreen(
    modifier: Modifier = Modifier,
    navigator: EditProfileNavigator,
) {
    val viewModel: EditProfileImageViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val toast = LocalToast.current
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { imageUrl ->
            viewModel.postIntent(
                EditProfileImageIntent.UpdateProfileImage(
                    uri = imageUrl,
                    context = context,
                )
            )
        }
    )

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            EditProfileImageSideEffect.ProfileImageSet -> toast.showSuccessToast(
                message = context.getString(
                    R.string.edit_profile_success
                )
            )

            EditProfileImageSideEffect.ProfileImageBadRequest -> toast.showErrorToast(
                message = context.getString(
                    R.string.edit_profile_fail
                )
            )
        }
    }
    DmsScaffold(
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.edit_profile)) },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = DmsIcon.Back),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            SetImage(
                uiState = uiState,
                onChangeImage = {
                    val mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    val request = PickVisualMediaRequest(mediaType)
                    activityResultLauncher.launch(request)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = {
                    viewModel.postIntent(
                        EditProfileImageIntent.EditProfile
                    )
                },
                enabled = uiState.buttonEnabled,
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}

@Composable
private fun SetImage(
    uiState: EditProfileImageUiState,
    onChangeImage: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        AsyncImage(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .clickable(
                    onClick = onChangeImage
                ),
            contentScale = ContentScale.Crop,
            model = uiState.uri ?: DmsIcon.ProfileDefault,
            contentDescription = stringResource(id = R.string.edit_profile_profile_image),
        )
        Image(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(DmsTheme.colorScheme.primary)
                .padding(PaddingDefaults.Small)
                .clickable(onClick = onChangeImage),
            painter = painterResource(id = DmsIcon.Plus),
            contentDescription = stringResource(id = R.string.edit_profile_profile_add),
        )
    }
}
