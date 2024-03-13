package team.aliens.dms.android.core.school.datastore.store

import team.aliens.dms.android.core.school.Features

abstract class FeaturesStore {

    abstract fun loadFeatures(): Features

    abstract suspend fun storeFeatures(features: Features)

    abstract suspend fun clearFeatures()
}
