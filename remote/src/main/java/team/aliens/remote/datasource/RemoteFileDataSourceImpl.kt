package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteFileDataSource
import team.aliens.domain._model.file.FetchPreSignedUrlOutput
import team.aliens.domain._model.file.UploadFileInput
import team.aliens.domain._model.file.UploadFileOutput
import team.aliens.remote.common.HttpProperty
import team.aliens.remote.common.HttpProperty.Header.ContentType
import team.aliens.remote.http.FileUploadClient
import team.aliens.remote.model.file.toDomain
import team.aliens.remote.service.FileService
import team.aliens.remote.util.sendHttpRequest
import team.aliens.remote.util.toMultipart
import java.io.File
import javax.inject.Inject

class RemoteFileDataSourceImpl @Inject constructor(
    private val fileService: FileService,
    private val fileUploadClient: FileUploadClient,
) : RemoteFileDataSource {

    override suspend fun uploadFile(
        input: UploadFileInput,
    ): UploadFileOutput {
        val file = input.file

        return sendHttpRequest {
            fileService.uploadFile(
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
            fileService.fetchPreSignedUrl(
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
