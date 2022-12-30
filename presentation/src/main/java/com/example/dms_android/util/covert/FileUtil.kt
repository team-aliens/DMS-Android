@file:Suppress(
    "MagicNumber",
    "TooGenericExceptionCaught",
    "SwallowedException",
    "UseCheckOrError",
)

package com.example.dms_android.util.covert

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object FileUtil {
    /**
     * 안드로이드 정책에 따라 이미지 파일의 절대값을 불러올 수 없습니다.
     * 이에 대응하기 위해 임시파일을 생성합니다.
     */
    fun createTempFile(context: Context, fileName: String): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    /**
     * 파일 내용의 Stream 을 복사합니다.
     */
    fun copyToFile(context: Context, uri: Uri, file: File) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(4 * 1024)
        while (true) {
            val byteCount = inputStream!!.read(buffer)
            if (byteCount < 0) break
            outputStream.write(buffer, 0, byteCount)
        }

        outputStream.flush()
    }
}

fun File.limitSize(): File {
    try {
        val option = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            inSampleSize = 6
        }

        var inputStream = FileInputStream(this)
        BitmapFactory.decodeStream(inputStream, null, option)
        inputStream.close()

        val requiredSize = 75

        var scale = 1
        while (option.outWidth / scale / 2 >= requiredSize && option.outHeight / scale / 2 >= requiredSize) {
            scale *= 2
        }

        val option2 = BitmapFactory.Options().apply {
            inSampleSize = scale
        }
        inputStream = FileInputStream(this)

        val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, option2)
        inputStream.close()

        this.createNewFile()
        val outStream = FileOutputStream(this)

        selectedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outStream)

        return this
    } catch (e: Exception) {
        throw IllegalStateException("limitSize error")
    }
}
