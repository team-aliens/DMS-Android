package team.aliens.domain._model.student

/**
 * A data class of app feature
 * @property serviceName feature's name
 * @property enabled a boolean value whether feature is enabled
 */
data class Feature(
    val serviceName: String,
    val enabled: Boolean,
)
