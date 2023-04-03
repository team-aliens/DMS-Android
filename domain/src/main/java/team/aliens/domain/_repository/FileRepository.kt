package team.aliens.domain._repository

import team.aliens.domain._model.file.FetchPreSignedUrlOutput
import team.aliens.domain._model.file.UploadFileOutput
import java.io.File

interface FileRepository {

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
