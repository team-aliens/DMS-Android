package team.aliens.domain.usecase.file

import team.aliens.domain._model.file.UploadFileInput
import team.aliens.domain._model.file.UploadFileOutput
import team.aliens.domain._repository.FileRepository
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
