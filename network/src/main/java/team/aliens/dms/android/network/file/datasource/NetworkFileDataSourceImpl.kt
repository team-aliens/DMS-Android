package team.aliens.dms.android.network.file.datasource

import team.aliens.dms.android.core.network.util.handleNetworkRequest
import team.aliens.dms.android.network.file.apiservice.FileApiService
import team.aliens.dms.android.network.file.model.FetchPresignedUrlResponse
import team.aliens.dms.android.network.file.model.UploadFileResponse
import java.io.File
import javax.inject.Inject

internal class NetworkFileDataSourceImpl @Inject constructor(
    private val fileApiService: FileApiService
) : NetworkFileDataSource() {
    override suspend fun uploadFile(file: File): UploadFileResponse =
        handleNetworkRequest { fileApiService.uploadFile(file) }

    override suspend fun fetchPresignedUrl(fileName: String): FetchPresignedUrlResponse =
        handleNetworkRequest { fileApiService.fetchPresignedUrl(fileName) }
}
