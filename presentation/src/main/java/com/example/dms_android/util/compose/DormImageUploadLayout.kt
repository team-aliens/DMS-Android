package com.example.dms_android.util.compose

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.design_system.color.DormColor
import com.example.design_system.modifier.dormClickable
import com.example.dms_android.R
import com.example.dms_android.util.covert.UriUtil
import com.example.dms_android.util.covert.parseBitmap
import java.io.File

private const val TakePhotoError: String = "이미지를 가져오던 중 오류가 발생하였습니다."

private val ImageSize: Dp = 100.dp

@Composable
fun DormImageUploadLayout(
    image: String,
    onImageChanged: (File) -> Unit,
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
                    onImageChanged(
                        UriUtil.toFile(
                            context = context,
                            uri = uri,
                        )
                    )
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
        modifier = Modifier
            .size(ImageSize),
        contentAlignment = Alignment.BottomEnd,
    ) {
        if (bitmap == null) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(ImageSize)
                    .clip(CircleShape)
                    .dormClickable {
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
        putExtra(
            Intent.EXTRA_MIME_TYPES,
            arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
        )
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
    }

@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
fun PreviewImageUpload() {
    DormImageUploadLayout(
        image = "url",
        onImageChanged = { img ->
        },
        onError = { error ->
            print(error)
        }
    )
}
