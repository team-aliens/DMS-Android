package team.aliens.domain.model.file

/**
 * A response returned when uploading file succeed
 * @property fileUrl an url of file, uploaded on server
 */
data class UploadFileOutput(
    val fileUrl: String,
)
