package team.aliens.dms_android.network.apiservice

import okhttp3.MultipartBody
import retrofit2.http.*
import team.aliens.dms_android.network.annotation.RequiresAccessToken
import team.aliens.dms_android.network.common.HttpPath.File.FetchPreSignedUrl
import team.aliens.dms_android.network.common.HttpPath.File.UploadFile
import team.aliens.dms_android.network.common.HttpProperty.QueryString.FileName
import team.aliens.dms_android.network.model.file.FetchPreSignedUrlResponse
import team.aliens.dms_android.network.model.file.UploadFileResponse

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
