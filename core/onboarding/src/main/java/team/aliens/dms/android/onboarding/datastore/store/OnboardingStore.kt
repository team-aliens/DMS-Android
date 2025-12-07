package team.aliens.dms.android.onboarding.datastore.store

internal abstract class OnboardingStore {
    abstract suspend fun setOnboardingCompleted(isCompleted: Boolean)

    abstract suspend fun getOnboardingCompleted(): Boolean
}
