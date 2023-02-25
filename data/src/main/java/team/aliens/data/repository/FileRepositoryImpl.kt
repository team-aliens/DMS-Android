package team.aliens.data.repository

import android.util.Log
import team.aliens.data.remote.datasource.declaration.RemoteFileDataSource
import team.aliens.data.remote.response.file.toEntity
import team.aliens.domain.entity.file.FileEntity
import team.aliens.domain.repository.FileRepository
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val remoteFileDataSource: RemoteFileDataSource,
) : FileRepository {

    override suspend fun uploadFile(
        file: File,
    ): FileEntity {
        Log.e("FILE", "uploadFile: $file", ) // TODO remove log
        return remoteFileDataSource.uploadFile(file).toEntity()
    }
}