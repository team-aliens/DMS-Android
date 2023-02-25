package team.aliens.data.remote.datasource.declaration

import team.aliens.data.remote.response.file.FileResponse
import java.io.File

interface RemoteFileDataSource {
    suspend fun uploadFile(
        file: File,
    ): FileResponse
}
