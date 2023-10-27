package com.example.foodrecipe.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.data.DataStoreRepository
import com.example.foodrecipe.util.Constants.Companion.API_KEY
import com.example.foodrecipe.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipe.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipe.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foodrecipe.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foodrecipe.util.Constants.Companion.QUERY_API_KEY
import com.example.foodrecipe.util.Constants.Companion.QUERY_DIET
import com.example.foodrecipe.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foodrecipe.util.Constants.Companion.QUERY_NUMBER
import com.example.foodrecipe.util.Constants.Companion.QUERY_SEARCH
import com.example.foodrecipe.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {
//  ViewModel is typically used for storing data related to the
//  UI state, such as the current screen or user input, while AndroidViewModel is often used for
//  storing data related to the application state, such as user preferences or data from a database.
    val readBackOnline =dataStoreRepository.readBackOnline.asLiveData()
    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    fun saveMealAndDietType(mealType:String,mealTypeId:Int,dietType:String,dietTypeId:Int)=
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType,mealTypeId,dietType,dietTypeId)
        }
    fun saveBackOnline(backOnline:Boolean)=
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepository.saveBackOnline(backOnline)
        }
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        viewModelScope.launch {
            readMealAndDietType.collect{value ->
                mealType = value.selectedMealType
                dietType = value.selectedMealType
            }
        }
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun applySearchQuery(searchQuery: String):HashMap<String,String>{
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] ="true"
        return queries
    }
    fun showNetworkStatus(){
        if(!networkStatus){
            Toast.makeText(getApplication(),"No Internet Connection",Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        }else if (networkStatus){
            if (backOnline){
                Toast.makeText(getApplication(),"Back Online ",Toast.LENGTH_SHORT).show()
                saveBackOnline(false)

            }
        }
    }
}