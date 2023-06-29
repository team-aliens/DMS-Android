package team.aliens.dms_android.common

import android.os.Bundle
import androidx.navigation.NavType
import java.util.UUID

@Suppress("DEPRECATION")
internal val NavType.Companion.UuidType: NavType<UUID>
    get() = object : NavType<UUID>(false) {
        override val name: String
            get() = "uuid"

        override fun put(bundle: Bundle, key: String, value: UUID) {
            bundle.putString(key, value.toString())
        }

        override fun get(bundle: Bundle, key: String): UUID {
            return bundle[key] as UUID
        }

        override fun parseValue(value: String): UUID {
            return UUID.fromString(value)
        }
    }
