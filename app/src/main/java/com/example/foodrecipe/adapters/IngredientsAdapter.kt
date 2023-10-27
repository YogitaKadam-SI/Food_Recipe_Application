package com.example.foodrecipe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodrecipe.ExtendedIngredient
import com.example.foodrecipe.R
import com.example.foodrecipe.ui.RecipesDiffUtil
import com.example.foodrecipe.util.Constants.Companion.BASE_IMAGE_URL
import java.util.*


class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()
     private lateinit var imageView : ImageView
     private lateinit var nameTextView : TextView
     private lateinit var amountTextView : TextView
     private lateinit var unitTextView : TextView
     private lateinit var consistencyTextView : TextView
     private lateinit var originalTextView : TextView

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout , parent , false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //val itemView = holder.itemView

        imageView = holder.itemView.findViewById(R.id.ingredient_imageView)
        nameTextView = holder.itemView.findViewById(R.id.ingredient_name)
        amountTextView = holder.itemView.findViewById(R.id.ingredient_amount)
        unitTextView = holder.itemView.findViewById(R.id.ingredient_unit)
        consistencyTextView = holder.itemView.findViewById(R.id.ingredient_consistency)
        originalTextView = holder.itemView.findViewById(R.id.ingredient_original)


        imageView.load(BASE_IMAGE_URL + ingredientsList[position].image){
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        nameTextView.text = ingredientsList[position].name.capitalize(Locale.ROOT)
        amountTextView.text = ingredientsList[position].amount.toString()
        unitTextView.text = ingredientsList[position].unit
        consistencyTextView.text = ingredientsList[position].consistency
        originalTextView.text = ingredientsList[position].original



    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientsList , newIngredients)

        val diffUtilResult = DiffUtil.calculateDiff((ingredientsDiffUtil))
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this )
    }
}