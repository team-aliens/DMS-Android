package team.aliens.data.remote.api

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import team.aliens.data.remote.response.file.FileResponse
import team.aliens.data.remote.url.DmsUrl

interface FileApi {

    @POST(DmsUrl.uploadFile)
    @Multipart
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
    ): FileResponse
}
