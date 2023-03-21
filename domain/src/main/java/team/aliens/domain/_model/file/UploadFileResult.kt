package team.aliens.domain._model.file

/**
 * @author junsuPark
 * A response returned when uploading file succeed
 * @property fileUrl an url of file, uploaded on server
 */
data class UploadFileResult(
    val fileUrl: String,
)
