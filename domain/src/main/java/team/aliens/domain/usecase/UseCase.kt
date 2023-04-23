package team.aliens.domain.usecase

@Deprecated("no more UseCase base class")
abstract class UseCase<I, O> {
    abstract suspend fun execute(data: I): O
}