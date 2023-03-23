package team.aliens.data._repository

import team.aliens.domain._model.file.UploadFileOutput
import team.aliens.domain._repository.FileRepository
import java.io.File

class FileRepositoryImpl(
    // private val remoteFileDataSource: RemoteFileDataSource,
) : FileRepository {

    override suspend fun uploadFile(
        file: File,
    ): UploadFileOutput {
        TODO("Not yet implemented")
    }
}
