package team.aliens.dms.android.data.file.mapper

import team.aliens.dms.android.data.file.model.FileUrl
import team.aliens.dms.android.data.file.model.PresignedFileUrl
import team.aliens.dms.android.network.file.model.FetchFileUrlResponse
import team.aliens.dms.android.network.file.model.FetchPresignedUrlResponse

internal fun FetchPresignedUrlResponse.toModel(): PresignedFileUrl = PresignedFileUrl(
    fileUploadUrl = this.fileUploadUrl,
    fileUrl = this.fileUrl,
)

internal fun FetchFileUrlResponse.toModel(): FileUrl = FileUrl(
    fileUrl = this.fileUrl,
)
