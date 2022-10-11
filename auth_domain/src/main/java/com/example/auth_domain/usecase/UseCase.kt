package com.example.auth_domain.usecase

abstract class UseCase<I, O> {
    abstract suspend fun execute(data: I): O
}