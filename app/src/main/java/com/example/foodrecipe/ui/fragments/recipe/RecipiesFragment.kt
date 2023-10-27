package com.example.foodrecipe.ui.fragments.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.R
import com.example.foodrecipe.viewmodels.MainViewModel
import com.example.foodrecipe.adapters.RecipesAdapter
import com.example.foodrecipe.databinding.FragmentRecipiesBinding
import com.example.foodrecipe.ui.observeOnce
import com.example.foodrecipe.util.NetworkListener
import com.example.foodrecipe.util.NetworkResult
import com.example.foodrecipe.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class RecipiesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args by navArgs<RecipiesFragmentArgs>()
    private var _binding: FragmentRecipiesBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { RecipesAdapter() }

    //private lateinit var mView: View
    private var recyclerView: RecyclerView? = null

    private var loader: ProgressBar? = null
    private val mainViewModel: MainViewModel by viewModels()
    private val recipesViewModel: RecipesViewModel by viewModels()
    private lateinit var networkListener: NetworkListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipiesBinding.inflate(inflater, container, false)
        Log.i("RecipiesFragment", "onCreateView")
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_recipes)
        loader = view.findViewById(R.id.loader)
        setupRecyclerView()
        //requestApiData()

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner,{
            recipesViewModel.backOnline =it
        })
        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()

                }
        }

        binding.recipesFab.setOnClickListener {
            if (recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipiesFragment_to_recipesBottomSheet)
            } else {
                recipesViewModel.showNetworkStatus()
            }
        }
    }

    private fun setupRecyclerView() {
        val adapter = mAdapter
        recyclerView?.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu,menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled =true
        searchView?.setOnQueryTextListener(this)


    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    database.getOrNull(0)?.results?.let { mAdapter.setData(it) }
                    hideLoader()
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "readDatabase caled!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        //mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            Log.i("RECIPE DATA RESPONSE:", response.toString())
            when (response) {
                is NetworkResult.Success -> {
                    hideLoader()
                    response.data?.let { mAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideLoader()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showLoaderEffect()
                }

            }
        })
    }

    private fun searchApiData(searchQuery:String){
        showLoaderEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchedRecipesResponse.observe(viewLifecycleOwner,{response ->
            when(response){
                is NetworkResult.Success -> {
                    hideLoader()
                    val foodRecipe = response.data
                    foodRecipe?.let{mAdapter.setData(it)}
                }
                is NetworkResult.Error -> {
                    hideLoader()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showLoaderEffect()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                database
                if (database.isNotEmpty()) {
                    database.getOrNull(0)?.results?.let { mAdapter.setData(it) }
                }
            })
        }
    }

    /* private fun applyQueries(): HashMap<String, String> {
         val queries: HashMap<String, String> = HashMap()
         queries["number"] = DEFAULT_RECIPES_NUMBER
         queries["apiKey"] = API_KEY
         queries["type"] = DEFAULT_MEAL_TYPE
         queries["diet"] = DEFAULT_DIET_TYPE
         queries["addRecipeInformation"] = "true"
         queries["fillIngredients"] = "true"

         return queries
     }*/


    private fun hideLoader() {
        loader?.isVisible = false
    }

    private fun showLoaderEffect() {
        loader?.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}