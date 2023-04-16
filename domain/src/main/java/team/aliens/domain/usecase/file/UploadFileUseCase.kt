package team.aliens.domain.usecase.file

import team.aliens.domain._model.file.UploadFileOutput
import team.aliens.domain._repository.FileRepository
import java.io.File
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(
    private val fileRepository: FileRepository,
) {
    suspend operator fun invoke(
        file: File,
    ): UploadFileOutput {
        return fileRepository.uploadFile(file)
    }
}
