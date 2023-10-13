sealed class Modules(
    val path: String,
) {
    object App : Modules(path = ":app")
    object Data : Modules(path = ":data")
    object Database : Modules(path = ":database")
    object DesignSystem : Modules(path = ":design-system")
    object Domain : Modules(path = ":domain")
    object Feature : Modules(path = ":feature")
    object Network : Modules(path = ":network")

    object Core {
        object Database : Modules(path = ":core:database")
        object DataStore : Modules(path = ":core:datastore")
        object Jwt : Modules(path = ":core:jwt")
        object Network : Modules(path = ":core:network")
        object Project : Modules(path = ":core:project")
        object School : Modules(path = ":core:school")
    }

    object Shared {
        object Date : Modules(path = ":shared:date")
    }
}
