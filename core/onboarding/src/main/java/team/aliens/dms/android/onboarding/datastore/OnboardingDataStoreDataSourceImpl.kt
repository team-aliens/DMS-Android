package team.aliens.dms.android.onboarding.datastore

import team.aliens.dms.android.onboarding.datastore.store.OnboardingStore
import javax.inject.Inject

internal class OnboardingDataStoreDataSourceImpl @Inject constructor(
    private val onboardingStore: OnboardingStore,
) : OnboardingDataStoreDataSource() {
    override suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        onboardingStore.setOnboardingCompleted(isCompleted)
    }

    override suspend fun getOnboardingCompleted(): Boolean {
        return onboardingStore.getOnboardingCompleted()
    }
}
