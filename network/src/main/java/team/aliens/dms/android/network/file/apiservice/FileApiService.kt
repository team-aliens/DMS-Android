package team.aliens.dms.android.network.file.apiservice

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Url
import team.aliens.dms.android.network.file.model.FetchPresignedUrlResponse
import team.aliens.dms.android.network.file.model.UploadFileResponse
import java.io.File

internal interface FileApiService {

    @GET("/files/url")
    suspend fun fetchPresignedUrl(
        @Query("file_name") fileName: String,
    ): FetchPresignedUrlResponse

    @PUT
    suspend fun uploadFile(
        @Url presignedUrl: String,
        @Body file: RequestBody,
    )
}
