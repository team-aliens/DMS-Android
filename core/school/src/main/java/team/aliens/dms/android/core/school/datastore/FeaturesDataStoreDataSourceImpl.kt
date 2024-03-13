package team.aliens.dms.android.core.school.datastore

import team.aliens.dms.android.core.school.Features
import team.aliens.dms.android.core.school.datastore.store.FeaturesStore
import javax.inject.Inject

internal class FeaturesDataStoreDataSourceImpl @Inject constructor(
    private val featuresStore: FeaturesStore,
) : FeaturesDataStoreDataSource() {
    override fun loadFeatures(): Features = featuresStore.loadFeatures()

    override suspend fun storeFeatures(features: Features) {
        featuresStore.storeFeatures(features)
    }

    override suspend fun clearFeatures() {
        featuresStore.clearFeatures()
    }
}
