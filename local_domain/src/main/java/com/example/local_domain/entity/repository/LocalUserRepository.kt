package com.example.local_domain.entity.repository

import com.example.local_domain.entity.UserVisibleLocalEntity

interface LocalUserRepository {

    suspend fun setUserVisible(userVisibleLocalEntity: UserVisibleLocalEntity)
    suspend fun fetchUserVisible(): UserVisibleLocalEntity
}