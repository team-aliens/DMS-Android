package team.aliens.dms_android.util.covert

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

@Suppress("DEPRECATION", "NewApi")
fun Uri.parseBitmap(context: Context): Bitmap {
    return when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // 28
        true -> {
            val source = ImageDecoder.createSource(context.contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }
        else -> {
            MediaStore.Images.Media.getBitmap(context.contentResolver, this)
        }
    }
}
