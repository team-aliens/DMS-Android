package team.aliens.data._repository

import team.aliens.domain._model.file.FetchPreSignedUrlOutput
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

    override suspend fun fetchPreSignedUrl(
        fileName: String,
    ): FetchPreSignedUrlOutput {
        TODO("Not yet implemented")
    }

    override suspend fun uploadFileToPreSignedUrl(
        fileUploadUrl: String,
    ) {
        TODO("Not yet implemented")
    }
}
