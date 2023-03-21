package team.aliens.domain._repository

import team.aliens.domain._model.file.UploadFileResult
import java.io.File

interface FileRepository {

    suspend fun uploadFile(
        file: File,
    ): UploadFileResult
}
