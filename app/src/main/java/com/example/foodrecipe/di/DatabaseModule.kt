package com.example.foodrecipe.di

import android.content.Context
import androidx.room.Room
import com.example.foodrecipe.data.LocalDataSource
import com.example.foodrecipe.data.RemoteDataSource
import com.example.foodrecipe.data.Repository
import com.example.foodrecipe.data.database.RecipesDatabase
import com.example.foodrecipe.data.network.FoodRecipesApi
import com.example.foodrecipe.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    )= Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase)= database.recipesDao()
}