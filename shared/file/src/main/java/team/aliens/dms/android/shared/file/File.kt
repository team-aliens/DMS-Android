package team.aliens.dms.android.shared.file

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream

object File {
    fun toFile(
        context: Context,
        uri: Uri,
    ): File {
        val fileName = getFileName(
            context = context,
            uri = uri,
        )
        val file = createTempFile(
            context = context,
            fileName = fileName,
        )

        copyToFile(
            context = context,
            uri = uri,
            file = file,
        )

        return File(file.absolutePath)
    }

    private fun copyToFile(
        context: Context,
        uri: Uri,
        file: File,
    ) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(4 * 1024)
        while (true) {
            val byteCount = inputStream!!.read(buffer)
            if (byteCount < 0) break
            outputStream.write(buffer, 0, byteCount)
        }

        inputStream.close()
    }

    private fun getFileName(
        uri: Uri,
        context: Context,
    ): String {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        var fileName = ""

        cursor?.run {
            cursor.moveToFirst()
            val index = getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index != -1) {
                fileName = cursor.getString(index)
            }
        }
        cursor?.close()

        return fileName
    }

    private fun createTempFile(
        context: Context,
        fileName: String,
    ): File {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File(storageDir, fileName)
    }
}
