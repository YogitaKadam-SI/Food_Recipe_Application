package com.example.foodrecipe.data.database.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipe.models.FoodRecipe
import com.example.foodrecipe.models.Result
import com.example.foodrecipe.util.Constants.Companion.RECIPES_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = RECIPES_TABLE)
data class RecipesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val results: FoodRecipe?
)