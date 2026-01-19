package team.aliens.dms.android.network.file.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import team.aliens.dms.android.core.network.util.RequestType
import team.aliens.dms.android.network.file.apiservice.FileApiService
import team.aliens.dms.android.network.file.model.FetchFileUrlResponse
import team.aliens.dms.android.network.file.model.FetchPresignedUrlResponse
import team.aliens.dms.android.shared.exception.util.suspendRunCatching
import java.io.File
import java.nio.file.Files
import javax.inject.Inject

internal class NetworkFileDataSourceImpl @Inject constructor(
    private val fileApiService: FileApiService,
) : NetworkFileDataSource() {
    override suspend fun fetchPresignedUrl(fileName: String): Result<FetchPresignedUrlResponse> =
        suspendRunCatching { fileApiService.fetchPresignedUrl(fileName) }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun uploadFile(presignedUrl: String, file: File): Result<FetchFileUrlResponse> =
        suspendRunCatching {
            fileApiService.uploadFile(
                presignedUrl = presignedUrl,
                file = Files.readAllBytes(file.toPath()).toRequestBody(
                    contentType = RequestType.Binary.toMediaTypeOrNull(),
                ),
            )
        }
}
