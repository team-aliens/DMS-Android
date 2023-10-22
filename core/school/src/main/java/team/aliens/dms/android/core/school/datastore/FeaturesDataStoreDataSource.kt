package team.aliens.dms.android.core.school.datastore

import team.aliens.dms.android.core.school.Features

internal abstract class FeaturesDataStoreDataSource {

    abstract fun loadFeatures(): Features

    abstract suspend fun storeFeatures(features: Features)
}
