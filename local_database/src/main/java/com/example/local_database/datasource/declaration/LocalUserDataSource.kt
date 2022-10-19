package com.example.local_database.datasource.declaration

import com.example.local_database.param.UserVisibleParam.FeaturesParam

interface LocalUserDataSource {

    suspend fun setUserVisibleInform(featuresParam: FeaturesParam)
    suspend fun fetchUserVisibleInform(): FeaturesParam
}
