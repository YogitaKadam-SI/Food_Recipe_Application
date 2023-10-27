package com.example.foodrecipe.data

import com.example.foodrecipe.data.database.RecipesDao
import com.example.foodrecipe.data.database.entities.FavoritesEntity
import com.example.foodrecipe.data.database.entities.FoodJokeEntity
import com.example.foodrecipe.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao){

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return recipesDao.readFavoriteRecipes()
    }

    fun readFoodJoke(): Flow<List<FoodJokeEntity>> {
        return recipesDao.readFoodJoke()
    }


     fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes((recipesEntity))
    }

    fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity){
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

     fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity){
        recipesDao.deleteFavoriteRecipes(favoritesEntity)
    }

    fun deleteAllFavoriteRecipes(){
        recipesDao.deleteAllFavoriteRecipes()
    }


}