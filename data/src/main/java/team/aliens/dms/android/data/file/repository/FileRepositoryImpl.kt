package team.aliens.dms.android.data.file.repository

import team.aliens.dms.android.data.file.mapper.toModel
import team.aliens.dms.android.data.file.model.FileUrl
import team.aliens.dms.android.data.file.model.PresignedFileUrl
import team.aliens.dms.android.network.file.datasource.NetworkFileDataSource
import java.io.File
import javax.inject.Inject

internal class FileRepositoryImpl @Inject constructor(
    private val networkFileDataSource: NetworkFileDataSource,
) : FileRepository() {
    override suspend fun uploadFile(file: File): FileUrl =
        networkFileDataSource.uploadFile(file = file).fileUrl

    override suspend fun fetchPresignedUrl(fileName: String): PresignedFileUrl =
        networkFileDataSource.fetchPresignedUrl(fileName = fileName).toModel()
}
