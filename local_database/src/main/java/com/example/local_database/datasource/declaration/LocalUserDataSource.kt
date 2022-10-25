package com.example.local_database.datasource.declaration

import com.example.local_database.param.FeaturesParam
import com.example.local_database.param.UserPersonalKeyParam

interface LocalUserDataSource {

    suspend fun setPersonalKey(personalKeyParam: UserPersonalKeyParam)
    suspend fun fetchPersonalKey(): UserPersonalKeyParam

    suspend fun setUserVisibleInform(featuresParam: FeaturesParam)
    suspend fun fetchUserVisibleInform(): FeaturesParam
}
