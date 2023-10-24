package team.aliens.dms.android.core.school

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.school.datastore.FeaturesDataStoreDataSource
import team.aliens.dms.android.core.school.network.FeaturesFetchingManager
import javax.inject.Inject

internal class SchoolProviderImpl @Inject constructor(
    private val featuresDataStoreDataSource: FeaturesDataStoreDataSource,
    private val jwtProvider: JwtProvider,
    private val featuresFetchingManager: FeaturesFetchingManager,
) : SchoolProvider() {

    private var _features: Features? = null
    override val features: Features
        get() = if (checkFeaturesAvailable().also(::updateFeaturesAvailable)) {
            _features!!
        } else {
            this.fetchFeatures()
        }

    private val _isFeaturesAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isFeaturesAvailable: StateFlow<Boolean> = _isFeaturesAvailable.asStateFlow()

    init {
        try {
            loadFeatures()
        } catch (_: Exception) {
        }
    }

    private fun loadFeatures(): Features =
        featuresDataStoreDataSource.loadFeatures().also(::updateFeatures)

    private fun fetchFeatures(): Features =
        featuresFetchingManager(accessToken = jwtProvider.cachedRefreshToken).toModel()
            .also(::updateFeatures)

    private fun checkFeaturesAvailable(): Boolean = _features != null

    private fun updateFeaturesAvailable(available: Boolean) {
        CoroutineScope(Dispatchers.Default).launch { _isFeaturesAvailable.emit(available) }
    }

    override fun updateFeatures(features: Features) {
        this._features = features

        CoroutineScope(Dispatchers.IO).launch {
            featuresDataStoreDataSource.storeFeatures(features)
            withContext(Dispatchers.Default) {
                updateFeaturesAvailable(true)
            }
        }
    }
}
