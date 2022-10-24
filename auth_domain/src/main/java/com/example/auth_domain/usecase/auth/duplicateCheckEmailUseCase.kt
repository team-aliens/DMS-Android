package com.example.auth_domain.usecase.auth

import com.example.auth_domain.repository.UserRepository
import com.example.auth_domain.usecase.UseCase
import javax.inject.Inject

class duplicateCheckEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        userRepository.duplicateCheckEmail(data)
    }
}