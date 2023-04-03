package team.aliens.data._repository

import team.aliens.data._datasource.remote.RemoteFileDataSource
import team.aliens.domain._model.file.FetchPreSignedUrlOutput
import team.aliens.domain._model.file.UploadFileOutput
import team.aliens.domain._repository.FileRepository
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val remoteFileDataSource: RemoteFileDataSource,
) : FileRepository {

    override suspend fun uploadFile(
        file: File,
    ): UploadFileOutput {
        return remoteFileDataSource.uploadFile(
            file = file,
        )
    }

    override suspend fun fetchPreSignedUrl(
        fileName: String,
    ): FetchPreSignedUrlOutput {
        return remoteFileDataSource.fetchPreSignedUrl(
            fileName = fileName,
        )
    }

    override suspend fun uploadFileToPreSignedUrl(
        file: File,
        fileUploadUrl: String,
    ) {
        return remoteFileDataSource.uploadFileToPreSignedUrl(
            file = file,
            fileUploadUrl = fileUploadUrl,
        )
    }
}
