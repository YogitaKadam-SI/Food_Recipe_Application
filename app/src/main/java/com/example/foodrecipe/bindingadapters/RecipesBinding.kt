package com.example.foodrecipe.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodrecipe.data.database.entities.RecipesEntity
import com.example.foodrecipe.models.FoodRecipe
import com.example.foodrecipe.util.NetworkResult

class RecipesBinding {
    companion object{
        @BindingAdapter("readApiResponse3","readDatabase3",requireAll = true)
        @JvmStatic
        fun errorimageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ){
if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
    imageView.visibility = View.VISIBLE
}else if (apiResponse is NetworkResult.Loading){
    imageView.visibility = View.INVISIBLE
}else if (apiResponse is NetworkResult.Success){
    imageView.visibility = View.INVISIBLE
}
        }
@BindingAdapter("readApiResponse2","readDatabase2",requireAll = true)
@JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ){
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            }else if (apiResponse is NetworkResult.Loading){
                textView.visibility = View.INVISIBLE
            }else if (apiResponse is NetworkResult.Success){
                textView.visibility = View.INVISIBLE
            }

    }
}}