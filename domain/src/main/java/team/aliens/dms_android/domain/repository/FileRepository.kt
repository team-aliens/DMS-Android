package team.aliens.dms_android.domain.repository

import team.aliens.dms_android.domain.model.file.FetchPreSignedUrlOutput
import team.aliens.dms_android.domain.model.file.UploadFileInput
import team.aliens.dms_android.domain.model.file.UploadFileOutput
import java.io.File

interface FileRepository {

    suspend fun uploadFile(
        input: UploadFileInput,
    ): UploadFileOutput

    suspend fun fetchPreSignedUrl(
        fileName: String,
    ): FetchPreSignedUrlOutput

    suspend fun uploadFileToPreSignedUrl(
        file: File,
        fileUploadUrl: String,
    )
}
