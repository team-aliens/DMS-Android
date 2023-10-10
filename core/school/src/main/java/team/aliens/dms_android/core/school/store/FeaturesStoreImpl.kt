package team.aliens.dms_android.core.school.store

import team.aliens.dms_android.core.school.datastore.FeaturesDataStoreDataSource
import team.aliens.dms_android.core.school.network.FeaturesManager
import javax.inject.Inject

internal class FeaturesStoreImpl @Inject constructor(
    val featuresDataStoreDataSource: FeaturesDataStoreDataSource,
    val featuresManager: FeaturesManager,
) : FeaturesStore()
