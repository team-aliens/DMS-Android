package team.aliens.dms.android.feature.profile.ui

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.feature.profile.viewmodel.SelectProfileViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun SelectProfile() {
    val request = rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)
    val viewModel: SelectProfileViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        request.launchPermissionRequest()
    }

    SelectProfileScreen(
        onBackPressed = {},
        uriList = state.uriList.toPersistentList(),
        selectedUris = state.selectedUris.toPersistentSet(),
        onImageSelected = viewModel::selectImage,
    )
}

@Composable
private fun SelectProfileScreen(
    onBackPressed: () -> Unit,
    uriList: ImmutableList<String>,
    selectedUris: ImmutableSet<String>,
    onImageSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DmsTopAppBar(
            onBackPressed = onBackPressed,
            actions = {
                Text(
                    text = "선택",
                    color = DmsTheme.colorScheme.primaryContainer,
                    style = DmsTheme.typography.bodyM,
                )
            }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(uriList) { uri ->
                val isSelected = selectedUris.contains(uri)
                ImageItem(
                    imageUri = uri,
                    isSelected = isSelected,
                    onClick = { onImageSelected(uri) },
                )
            }
        }
    }
}

@Composable
fun ImageItem(
    imageUri: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.4f)) // 반투명 검은색
            )
        }
        Icon(
            imageVector = if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
            contentDescription = if (isSelected) "선택됨" else "선택 안됨",
            tint = if (isSelected) DmsTheme.colorScheme.onPrimary else Color.White, // 선택되면 강조색, 아니면 흰색
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(24.dp)
                .background(
                    color = if (!isSelected) Color.Black.copy(alpha = 0.2f) else Color.Transparent,
                    shape = CircleShape
                )
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(3.dp, DmsTheme.colorScheme.onPrimary) // 굵은 테두리
            )
        }
    }
}
//
//@Composable
//fun TestUi() {
//    val zoomImageState = rememberZoomableImageState(
//        zoomableState = rememberZoomableState(
//            zoomSpec = ZoomSpec(
//                maxZoomFactor = 3f,
//                preventOverOrUnderZoom = false
//            )
//        )
//    )
//    val currentZoomScale by remember {
//        derivedStateOf {
//            // contentTransformation이 null이 아닐 때 scaleX 값을 가져옴 (기본값 1f)
//            zoomImageState.zoomableState.contentTransformation.scale.scaleX
//        }
//    }
//    Log.d("TEST", currentZoomScale.toString())
//    Box(modifier = Modifier.fillMaxSize().background(Color.Black).onSizeChanged {
//
//    }) {
//        // 3. Telephoto의 ZoomableAsyncImage 사용
//        ZoomableAsyncImage(
//            model = "https://picsum.photos/1080/1920",
//            contentDescription = "Zoomable Photo",
//            state = zoomImageState,
//            modifier = Modifier.fillMaxSize(),
//            // 클릭 리스너 등 필요 시 추가
//            onClick = { /* 클릭 시 UI 토글 등 */ }
//        )
//
//    }
//}
