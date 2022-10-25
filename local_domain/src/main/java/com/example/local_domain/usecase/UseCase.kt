package com.example.local_domain.usecase

abstract class UseCase<I, O> {
    abstract suspend fun execute(data: I): O
}