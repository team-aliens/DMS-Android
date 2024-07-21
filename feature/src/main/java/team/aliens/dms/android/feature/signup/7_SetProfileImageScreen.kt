package team.aliens.dms.android.feature.signup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsIcon
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun SetProfileImageScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    viewModel: SignUpViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val toast = LocalToast.current
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { imageUrl ->
            viewModel.postIntent(SignUpIntent.UpdateProfileImage(uri = imageUrl, context = context))
        }
    )

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SignUpSideEffect.ProfileImageSet -> navigator.openTerms()
            SignUpSideEffect.ProfileImageBadRequest -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_profile_error_load_image_error)
            )

            else -> { /* explicit blank */
            }
        }
    }
    
    Scaffold(
        topBar = {
            DmsTopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigator::popUpToSetPassword) {
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
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .topPadding(BannerDefaults.DefaultTopSpace)
                    .startPadding(),
                message = { BannerDefaults.DefaultText(text = stringResource(id = R.string.sign_up_set_profile)) },
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
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
                Spacer(modifier = Modifier.weight(2f))
                Text(
                    modifier = Modifier
                        .clickable(
                            onClick = navigator::openTerms
                        )
                        .padding(
                            vertical = PaddingDefaults.Small,
                            horizontal = PaddingDefaults.Large,
                        ),
                    text = stringResource(
                        id = R.string.sign_up_profile__set_later,
                    ),
                    style = DmsTheme.typography.button,
                )
                Spacer(modifier = Modifier.height(20.dp))
                ContainedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding()
                        .bottomPadding(),
                    onClick = navigator::openTerms,
                    enabled = uiState.setProfileButtonEnabled,
                ) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    }
}

@Composable
private fun SetImage(
    uiState: SignUpUiState,
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
                    onClick = onChangeImage,
                ),
            contentScale = ContentScale.Crop,
            model = uiState.uri ?: DmsIcon.ProfileDefault,
            contentDescription = stringResource(id = R.string.sign_up_profile_image),
        )
        Image(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(DmsTheme.colorScheme.primary)
                .padding(PaddingDefaults.Small)
                .clickable(
                    onClick = onChangeImage,
                ),
            painter = painterResource(id = DmsIcon.Plus),
            contentDescription = stringResource(id = R.string.sign_up_profile_add),
        )
    }
}
