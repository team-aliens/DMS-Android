package com.example.local_domain.entity.repository

import com.example.local_domain.entity.UserVisibleLocalEntity.FeaturesParam

interface LocalUserRepository {

    suspend fun setUserVisible(featuresParam: FeaturesParam)
    suspend fun fetchUserVisible(): FeaturesParam
}