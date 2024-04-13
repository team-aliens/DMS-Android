package team.aliens.dms.android.data.file.repository

import team.aliens.dms.android.data.file.model.FileUrl
import team.aliens.dms.android.data.file.model.PresignedFileUrl
import java.io.File

abstract class FileRepository {

    abstract suspend fun uploadFile(file: File) : FileUrl

    abstract suspend fun fetchPresignedUrl(fileName: String) : PresignedFileUrl
}
