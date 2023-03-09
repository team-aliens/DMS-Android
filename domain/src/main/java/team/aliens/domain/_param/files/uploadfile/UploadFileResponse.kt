package team.aliens.domain._param.files.uploadfile

/**
 * @author junsuPark
 * A response returned when uploading file succeed
 * [fileUrl] an url of file, uploaded on server
 */
data class UploadFileResponse(
    val fileUrl: String,
)
