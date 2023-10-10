package team.aliens.dms_android.core.school

import team.aliens.dms_android.core.school.store.FeaturesStore
import javax.inject.Inject

object SchoolProvider : SchoolProviderInjectionDelegation()

abstract class SchoolProviderInjectionDelegation {
    @Inject
    lateinit var featuresStore: FeaturesStore
}
