package com.example.foodrecipe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodrecipe.data.database.entities.FavoritesEntity
import com.example.foodrecipe.data.database.entities.FoodJokeEntity
import com.example.foodrecipe.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class , FavoritesEntity::class , FoodJokeEntity::class],
    version = 2,
    exportSchema =false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase :RoomDatabase(){
abstract fun recipesDao(): RecipesDao
}