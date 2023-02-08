package team.aliens.dms_android.util.compose

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.modifier.dormClickable
import team.aliens.dms_android.util.covert.UriUtil
import team.aliens.dms_android.util.covert.parseBitmap
import team.aliens.presentation.R
import java.io.File

private const val TakePhotoError: String = "이미지를 가져오던 중 오류가 발생하였습니다."

private val ImageSize: Dp = 100.dp

@Composable
fun DormImageUploadLayout(
    imageFile: (File) -> Unit,
    onError: ((String) -> Unit)? = null,
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val takePhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        bitmap = uri.parseBitmap(context)
                    }
                    imageFile(UriUtil.toFile(
                        context = context,
                        uri = uri,
                    ))
                } ?: run {
                    if (onError != null) {
                        onError(TakePhotoError)
                    }
                }
            } else if (result.resultCode != Activity.RESULT_CANCELED) {
                if (onError != null) {
                    onError(TakePhotoError)
                }
            }
        }

    Box(
        modifier = Modifier.size(ImageSize),
        contentAlignment = Alignment.BottomEnd,
    ) {
        if (bitmap == null) {
            Box(
                modifier = Modifier
                    .size(ImageSize)
                    .background(
                        color = DormColor.Gray200,
                        shape = CircleShape,
                    )
                    .clip(CircleShape)
                    .dormClickable(
                        rippleEnabled = true,
                    ) {
                        takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                    },
            )
        } else {
            AsyncImage(
                model = bitmap,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(ImageSize)
                    .clip(CircleShape)
                    .dormClickable {
                        takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                    },
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_mypage_edit),
            contentDescription = null,
        )
    }
}

val takePhotoFromAlbumIntent =
    Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
        type = "image/*"
        action = Intent.ACTION_GET_CONTENT
        putExtra(Intent.EXTRA_MIME_TYPES,
            arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp"))
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
    }

@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
fun PreviewImageUpload() {
    DormImageUploadLayout(
        imageFile = {},
        onError = {},
    )
}
