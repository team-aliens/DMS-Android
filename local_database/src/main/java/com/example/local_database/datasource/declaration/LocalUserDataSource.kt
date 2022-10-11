package com.example.local_database.datasource.declaration

import com.example.local_database.param.UserVisibleParam
import com.example.local_domain.entity.UserVisibleLocalEntity

interface LocalUserDataSource {

    suspend fun setUserVisibleInform(userVisibleParam: UserVisibleParam)
    suspend fun fetchUserVisibleInform(): UserVisibleParam
}