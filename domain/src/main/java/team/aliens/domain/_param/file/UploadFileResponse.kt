package team.aliens.domain._param.file

/**
 * @author junsuPark
 * A response returned when uploading file succeed
 * @property fileUrl an url of file, uploaded on server
 */
data class UploadFileResponse(
    val fileUrl: String,
)
