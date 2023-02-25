package team.aliens.domain.repository

import team.aliens.domain.entity.file.FileEntity
import java.io.File

interface FileRepository {
    suspend fun uploadFile(
        file: File,
    ): FileEntity
}
