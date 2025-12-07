package team.aliens.dms.android.onboarding.datastore

abstract class OnboardingDataStoreDataSource {

    abstract suspend fun setOnboardingCompleted(isCompleted: Boolean)

    abstract suspend fun getOnboardingCompleted(): Boolean
}
