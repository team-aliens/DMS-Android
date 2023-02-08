package team.aliens.data.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OfflineCacheUtil<T> {

    private lateinit var fetchLocalData: suspend () -> T
    private lateinit var fetchRemoteData: suspend () -> T
    private var isNeedRefresh: suspend (localData: T, remoteData: T) -> Boolean =
        { localData, remoteData -> localData != remoteData }
    private lateinit var refreshLocalData: suspend (remoteData: T) -> Unit

    fun remoteData(fetchRemoteData: suspend () -> T): OfflineCacheUtil<T> =
        this.apply { this.fetchRemoteData = fetchRemoteData }

    fun localData(fetchLocalData: suspend () -> T): OfflineCacheUtil<T> =
        this.apply { this.fetchLocalData = fetchLocalData }

    fun compareData(isNeedRefresh: suspend (localData: T, remoteData: T) -> Boolean): OfflineCacheUtil<T> =
        this.apply { this.isNeedRefresh = isNeedRefresh }

    fun doOnNeedRefresh(refreshLocalData: suspend (remoteData: T) -> Unit): OfflineCacheUtil<T> =
        this.apply { this.refreshLocalData = refreshLocalData }

    fun createFlow(): Flow<T> = flow {
        try {
            val localData = fetchLocalData()
            emit(localData)

            val remoteData = fetchRemoteData()
            if (isNeedRefresh(localData, remoteData)) {
                refreshLocalData(remoteData)
                emit(remoteData)
            }
        } catch (e: NullPointerException) {
            val remoteData = fetchRemoteData()
            refreshLocalData(remoteData)
            emit(remoteData)
        }
    }

    fun createRemoteFlow(): Flow<T> = flow {
        val remoteData = fetchRemoteData()
        emit(remoteData)
    }
}