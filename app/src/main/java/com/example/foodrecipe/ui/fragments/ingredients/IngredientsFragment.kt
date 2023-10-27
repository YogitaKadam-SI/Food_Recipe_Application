package com.example.foodrecipe.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.R
import com.example.foodrecipe.adapters.IngredientsAdapter
import com.example.foodrecipe.models.Result
import com.example.foodrecipe.util.Constants.Companion.RECIPE_RESULT_KEY
//import kotlinx.android.synthetic.main.fragment_ingredients.view.*

class IngredientsFragment : Fragment() {

    private lateinit var ingredientsRecyclerView : RecyclerView

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        ingredientsRecyclerView = view.findViewById(R.id.ingredients_recyclerview)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setupRecyclerView(view)
        myBundle?.extendedIngredients?.let { mAdapter.setData(it)}
        return view

    }

    private fun setupRecyclerView(view: View) {

        //val adapter=IngredientsAdapter()

        ingredientsRecyclerView.adapter = mAdapter
        //view.ingredients_recyclerview.adapter = mAdapter
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


}