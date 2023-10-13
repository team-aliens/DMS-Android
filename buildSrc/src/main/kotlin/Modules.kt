sealed class Modules(
    val path: String,
) {
    object App : Modules(path = ":app")
    object Core {
        object Database : Modules(path = ":core:database")
        object DataStore : Modules(path = ":core:datastore")
        object Jwt : Modules(path = ":core:jwt")
        object Network : Modules(path = ":core:network")
        object Project : Modules(path = ":core:project")
        object School : Modules(path = ":core:school")
    }
}
