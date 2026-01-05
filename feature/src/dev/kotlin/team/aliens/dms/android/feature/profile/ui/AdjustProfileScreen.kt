package team.aliens.dms.android.feature.profile.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import me.saket.telephoto.zoomable.ZoomSpec
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage
import me.saket.telephoto.zoomable.rememberZoomableImageState
import me.saket.telephoto.zoomable.rememberZoomableState

@Composable
internal fun AdjustProfile(
    onBackPressed: () -> Unit,
    model: String,
) {
    AdjustProfileScreen(
        onBackPressed = onBackPressed,
        model = model
    )
}

@Composable
private fun AdjustProfileScreen(
    onBackPressed: () -> Unit,
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
            // contentTransformation이 null이 아닐 때 scaleX 값을 가져옴 (기본값 1f)
            zoomImageState.zoomableState.contentTransformation.scale.scaleX
        }
    }
    Log.d("TEST", currentZoomScale.toString())
    Box(modifier = Modifier.fillMaxSize().background(Color.Black).onSizeChanged {

    }) {
        // 3. Telephoto의 ZoomableAsyncImage 사용
        ZoomableAsyncImage(
            model = model,
            contentDescription = "Zoomable Photo",
            state = zoomImageState,
            modifier = Modifier.fillMaxSize(),
            // 클릭 리스너 등 필요 시 추가
            onClick = { /* 클릭 시 UI 토글 등 */ }
        )

    }
}
