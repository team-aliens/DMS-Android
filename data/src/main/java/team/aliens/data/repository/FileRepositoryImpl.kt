package team.aliens.data.repository

import team.aliens.data.datasource.remote.RemoteFileDataSource
import team.aliens.domain._model.file.FetchPreSignedUrlOutput
import team.aliens.domain._model.file.UploadFileInput
import team.aliens.domain._model.file.UploadFileOutput
import team.aliens.domain._repository.FileRepository
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val remoteFileDataSource: RemoteFileDataSource,
) : FileRepository {

    override suspend fun uploadFile(
        input: UploadFileInput,
    ): UploadFileOutput {
        return remoteFileDataSource.uploadFile(
            input = input,
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
