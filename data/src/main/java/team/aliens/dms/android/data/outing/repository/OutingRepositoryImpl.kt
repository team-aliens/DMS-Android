package team.aliens.dms.android.data.outing.repository

import javax.inject.Inject

class OutingRepositoryImpl @Inject constructor(
    private val outingNetworkDataSource: OutingNetworkDataSource,
) : OutingRepository() {

}
