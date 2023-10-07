package team.aliens.remote.apiservice

import okhttp3.MultipartBody
import retrofit2.http.*
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.HttpPath.File.FetchPreSignedUrl
import team.aliens.remote.common.HttpPath.File.UploadFile
import team.aliens.remote.common.HttpProperty.QueryString.FileName
import team.aliens.remote.model.file.FetchPreSignedUrlResponse
import team.aliens.remote.model.file.UploadFileResponse

interface FileApiService {

    @POST(UploadFile)
    @Multipart
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
    ): UploadFileResponse

    @GET(FetchPreSignedUrl)
    @RequiresAccessToken
    suspend fun fetchPreSignedUrl(
        @Query(FileName) fileName: String,
    ): FetchPreSignedUrlResponse
}
