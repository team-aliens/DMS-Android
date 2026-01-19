package team.aliens.dms.android.feature.profile.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.saket.telephoto.zoomable.ZoomSpec
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage
import me.saket.telephoto.zoomable.rememberZoomableImageState
import me.saket.telephoto.zoomable.rememberZoomableState
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsLayeredButton
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.core.designsystem.verticalPadding
import team.aliens.dms.android.feature.profile.viewmodel.AdjustProfileSideEffect
import team.aliens.dms.android.feature.profile.viewmodel.AdjustProfileViewModel
import team.aliens.dms.android.feature.remain.ui.component.DmsFloatingNotice

@Composable
internal fun AdjustProfile(
    onBackPressed: () -> Unit,
    model: String,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: AdjustProfileViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is AdjustProfileSideEffect.ProfileImageSet -> onBackPressed()
                is AdjustProfileSideEffect.ProfileImageBadRequest -> onShowSnackBar(
                    DmsSnackBarType.ERROR, "실패"
                )
            }
        }
    }

    AdjustProfileScreen(
        onBackPressed = onBackPressed,
        model = model,
        updateProfileImage = { viewModel.editProfile(model) }
    )
}

@Composable
private fun AdjustProfileScreen(
    onBackPressed: () -> Unit,
    updateProfileImage: () -> Unit,
    model: String,
) {
    val zoomImageState = rememberZoomableImageState(
        zoomableState = rememberZoomableState(
            zoomSpec = ZoomSpec(
                maxZoomFactor = 3f,
                preventOverOrUnderZoom = false
            )
        )
    )
    val currentZoomScale by remember {
        derivedStateOf {
            zoomImageState.zoomableState.contentTransformation.scale.scaleX
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        DmsTopAppBar(
            onBackPressed = onBackPressed
        )
        Column(
            modifier = Modifier
                .topPadding(68.dp)
                .horizontalPadding(10.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(DmsTheme.colorScheme.surface),
        ) {
            Text(
                modifier = Modifier
                    .topPadding(24.dp)
                    .horizontalPadding(16.dp),
                text = "이미지 조정",
                style = DmsTheme.typography.bodyB,
            )
            Box(
                modifier = Modifier
                    .horizontalPadding(16.dp)
                    .verticalPadding(24.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .onSizeChanged {

                    }
            ) {
                ZoomableAsyncImage(
                    modifier = Modifier.aspectRatio(1f),
                    model = model,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Zoomable Photo",
                    state = zoomImageState,
                )

            }
        }
        Spacer(modifier = Modifier.weight(1f))
        DmsLayeredButton(
            modifier = Modifier,
            text = "변경하기",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            isLoading = false,
            onClick = updateProfileImage,
        )
    }
}
