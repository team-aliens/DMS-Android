package team.aliens.domain.usecase.file

import team.aliens.domain.entity.file.FileEntity
import team.aliens.domain.repository.FileRepository
import team.aliens.domain.usecase.UseCase
import java.io.File
import javax.inject.Inject

class RemoteUploadFileUseCase @Inject constructor(
    private val fileRepository: FileRepository,
) : UseCase<File, FileEntity>() {
    override suspend fun execute(data: File): FileEntity {
        return fileRepository.uploadFile(data)
    }
}
