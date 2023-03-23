package team.aliens.domain._model.file

/**
 * A response returned when uploading file succeed
 * @property fileUrl an url of file, uploaded on server
 */
data class UploadFileOutput(
    val fileUrl: String,
)
