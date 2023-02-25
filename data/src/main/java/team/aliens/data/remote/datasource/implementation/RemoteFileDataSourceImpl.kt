package team.aliens.data.remote.datasource.implementation

import team.aliens.data.remote.api.FileApi
import team.aliens.data.remote.datasource.declaration.RemoteFileDataSource
import team.aliens.data.remote.response.file.FileResponse
import team.aliens.data.util.MultipartFormUtil
import team.aliens.data.util.sendHttpRequest
import java.io.File
import javax.inject.Inject

class RemoteFileDataSourceImpl @Inject constructor(
    private val fileApi: FileApi,
) : RemoteFileDataSource {
    override suspend fun uploadFile(
        file: File,
    ): FileResponse {

        val formedFile = MultipartFormUtil.getFileBody(
            "file",
            file,
        )

        return sendHttpRequest(
            httpRequest = {
                fileApi.uploadFile(formedFile)
            },
        )
    }
}
