package team.aliens.dms.android.network._legacy.datasource

import team.aliens.dms.android.data.datasource.remote.RemoteFileDataSource
import team.aliens.dms.android.network._legacy.apiservice.FileApiService
import team.aliens.dms.android.network.common.HttpProperty
import team.aliens.dms.android.network.common.HttpProperty.Header.ContentType
import team.aliens.dms.android.network.http.FileUploadClient
import team.aliens.dms.android.network.model.file.toDomain
import team.aliens.dms.android.network.util.sendHttpRequest
import team.aliens.dms.android.network.util.toMultipart
import team.aliens.dms.android.domain.model.file.FetchPreSignedUrlOutput
import team.aliens.dms.android.domain.model.file.UploadFileInput
import team.aliens.dms.android.domain.model.file.UploadFileOutput
import java.io.File
import javax.inject.Inject

class RemoteFileDataSourceImpl @Inject constructor(
    private val fileApiService: FileApiService,
    private val fileUploadClient: FileUploadClient,
) : RemoteFileDataSource {

    override suspend fun uploadFile(
        input: UploadFileInput,
    ): UploadFileOutput {
        val file = input.file

        return sendHttpRequest {
            fileApiService.uploadFile(
                file = file.toMultipart(
                    name = HttpProperty.Body.File,
                    mediaType = ContentType.Multipart.FormData,
                )
            )
        }.toDomain()
    }

    override suspend fun fetchPreSignedUrl(
        fileName: String,
    ): FetchPreSignedUrlOutput {
        return sendHttpRequest {
            fileApiService.fetchPreSignedUrl(
                fileName = fileName,
            )
        }.toDomain()
    }

    override suspend fun uploadFileToPreSignedUrl(
        file: File,
        fileUploadUrl: String,
    ) {

        val preSignedUrl = this.fetchPreSignedUrl(
            fileName = file.name,
        )

        return sendHttpRequest {
            fileUploadClient(
                file = file,
                fileUploadUrl = preSignedUrl.fileUploadUrl,
            )
        }
    }
}
