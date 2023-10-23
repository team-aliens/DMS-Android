package team.aliens.dms.android.core.school

import kotlinx.coroutines.flow.StateFlow

abstract class SchoolProvider {

    abstract val features: Features
    abstract val isFeaturesAvailable: StateFlow<Boolean>

    abstract fun updateFeatures(features: Features)
}
