package team.aliens.dms.android.data.file.repository

import team.aliens.dms.android.data.file.mapper.toModel
import team.aliens.dms.android.data.file.model.PresignedFileUrl
import team.aliens.dms.android.network.file.datasource.NetworkFileDataSource
import java.io.File
import javax.inject.Inject

internal class FileRepositoryImpl @Inject constructor(
    private val networkFileDataSource: NetworkFileDataSource,
) : FileRepository() {
    override suspend fun fetchPresignedUrl(fileName: String): PresignedFileUrl =
        networkFileDataSource.fetchPresignedUrl(fileName = fileName).toModel()

    override suspend fun uploadFile(presignedUrl: String, file: File) =
        networkFileDataSource.uploadFile(presignedUrl = presignedUrl, file = file)
}
