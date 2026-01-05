package team.aliens.dms.android.data.file.repository

import android.os.FileUriExposedException
import team.aliens.dms.android.data.file.model.FileUrl
import team.aliens.dms.android.data.file.model.PresignedFileUrl
import java.io.File

abstract class FileRepository {

    abstract suspend fun fetchPresignedUrl(fileName: String): PresignedFileUrl
    abstract suspend fun uploadFile(presignedUrl: String, file: File): FileUrl
}
