package team.aliens.dms.android.domain._legacy.usecase.file

import team.aliens.dms.android.domain.model.file.UploadFileInput
import team.aliens.dms.android.domain.model.file.UploadFileOutput
import team.aliens.dms.android.domain.repository.FileRepository
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(
    private val fileRepository: FileRepository,
) {
    suspend operator fun invoke(
        uploadFileInput: UploadFileInput,
    ): UploadFileOutput {
        return fileRepository.uploadFile(
            input = uploadFileInput,
        )
    }
}
