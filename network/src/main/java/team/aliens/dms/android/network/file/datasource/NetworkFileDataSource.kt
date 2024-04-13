package team.aliens.dms.android.network.file.datasource

import team.aliens.dms.android.network.file.model.FetchPresignedUrlResponse
import team.aliens.dms.android.network.file.model.UploadFileResponse
import java.io.File

abstract class NetworkFileDataSource {
    abstract suspend fun uploadFile(file: File) : UploadFileResponse

    abstract suspend fun fetchPresignedUrl(fileName: String) : FetchPresignedUrlResponse
}
