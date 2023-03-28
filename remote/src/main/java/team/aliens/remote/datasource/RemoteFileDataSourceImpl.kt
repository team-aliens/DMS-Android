package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteFileDataSource
import team.aliens.domain._model.file.FetchPreSignedUrlOutput
import team.aliens.domain._model.file.UploadFileOutput
import java.io.File

class RemoteFileDataSourceImpl : RemoteFileDataSource {

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
}
