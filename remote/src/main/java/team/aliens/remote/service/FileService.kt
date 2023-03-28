package team.aliens.remote.service

import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import team.aliens.remote.common.DormHttpPath.Files.FetchPreSignedUrl
import team.aliens.remote.common.DormHttpPath.Files.UploadFile
import team.aliens.remote.common.HttpProperty.QueryString.FileName
import team.aliens.remote.model.file.FetchPreSignedUrlResponse
import team.aliens.remote.model.file.FileResponse

interface FileService {

    @POST(UploadFile)
    @Multipart
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
    ): FileResponse

    @GET(FetchPreSignedUrl)
    suspend fun fetchPreSignedUrl(
        @Query(FileName) fileName: String,
    ): FetchPreSignedUrlResponse
}
