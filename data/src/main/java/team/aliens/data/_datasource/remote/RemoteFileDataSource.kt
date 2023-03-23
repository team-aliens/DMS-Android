package team.aliens.data._datasource.remote

import team.aliens.domain._model.file.UploadFileOutput
import java.io.File

interface RemoteFileDataSource {

    suspend fun uploadFile(
        file: File,
    ): UploadFileOutput
}
