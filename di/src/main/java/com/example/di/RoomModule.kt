package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.local_database.converter.MealListTypeConverter
import com.example.local_database.dao.MealDao
import com.example.local_database.database.DMSDataBase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideDMSDatabase(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): DMSDataBase = Room
        .databaseBuilder(context, DMSDataBase::class.java, "DMSDataBase")
        .addTypeConverter(MealListTypeConverter(moshi))
        .build()

    @Provides
    fun provideMealDao(
        dmsDataBase: DMSDataBase
    ): MealDao = dmsDataBase.mealDao()
}
