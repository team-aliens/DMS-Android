package team.aliens.dms.android.network.file.datasource

import team.aliens.dms.android.network.file.model.FetchPresignedUrlResponse
import java.io.File

abstract class NetworkFileDataSource {
    abstract suspend fun fetchPresignedUrl(fileName: String): FetchPresignedUrlResponse
    abstract suspend fun uploadFile(presignedUrl: String, file: File)
}
