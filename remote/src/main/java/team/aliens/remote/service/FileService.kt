package team.aliens.remote.service

import okhttp3.MultipartBody
import retrofit2.http.*
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.HttpPath.Files.FetchPreSignedUrl
import team.aliens.remote.common.HttpPath.Files.UploadFile
import team.aliens.remote.common.HttpProperty.QueryString.FileName
import team.aliens.remote.model.file.FetchPreSignedUrlResponse
import team.aliens.remote.model.file.UploadFileResponse

interface FileService {

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
