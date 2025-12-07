package team.aliens.dms.android.onboarding.datastore.store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import team.aliens.dms.android.core.datastore.OnboardingDataStore
import team.aliens.dms.android.core.datastore.PreferencesDataStore
import team.aliens.dms.android.core.datastore.util.transform
import javax.inject.Inject

internal class OnboardingStoreImpl @Inject constructor(
    @OnboardingDataStore private val onboardingDataStore: PreferencesDataStore,
) : OnboardingStore() {

    override suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        transform {
            onboardingDataStore.edit { preferences ->
                preferences[ONBOARDING_COMPLETED] = isCompleted
            }
        }
    }

    override suspend fun getOnboardingCompleted(): Boolean =
        onboardingDataStore.data.map { preferences ->
            preferences[ONBOARDING_COMPLETED] ?: false
        }.first()


    private companion object {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding-completed")
    }
}
