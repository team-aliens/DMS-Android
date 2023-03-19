package team.aliens.domain._entity.student

/**
 * @author junsuPark
 * A data class of app feature
 * @property serviceName feature's name
 * @property enabled a boolean value whether feature is enabled
 */
data class Feature(
    val serviceName: String,
    val enabled: Boolean,
)
