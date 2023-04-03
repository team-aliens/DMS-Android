package team.aliens.data._datasource.remote

import team.aliens.domain._model.file.FetchPreSignedUrlOutput
import team.aliens.domain._model.file.UploadFileOutput
import java.io.File

interface RemoteFileDataSource {

    suspend fun uploadFile(
        file: File,
    ): UploadFileOutput

    suspend fun fetchPreSignedUrl(
        fileName: String,
    ): FetchPreSignedUrlOutput

    suspend fun uploadFileToPreSignedUrl(
        file: File,
        fileUploadUrl: String,
    )
}
