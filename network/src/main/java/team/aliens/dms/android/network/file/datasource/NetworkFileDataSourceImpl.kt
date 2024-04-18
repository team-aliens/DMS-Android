package team.aliens.dms.android.network.file.datasource

import team.aliens.dms.android.core.network.util.handleNetworkRequest
import team.aliens.dms.android.network.file.apiservice.FileApiService
import team.aliens.dms.android.network.file.model.FetchPresignedUrlResponse
import java.io.File
import javax.inject.Inject

internal class NetworkFileDataSourceImpl @Inject constructor(
    private val fileApiService: FileApiService
) : NetworkFileDataSource() {
    override suspend fun fetchPresignedUrl(fileName: String): FetchPresignedUrlResponse =
        handleNetworkRequest { fileApiService.fetchPresignedUrl(fileName) }

    override suspend fun uploadFile(presignedUrl: String, file: File) =
        handleNetworkRequest {
            fileApiService.uploadFile(
                presignedUrl = presignedUrl,
                file = file,
            )
        }
}
