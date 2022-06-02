package com.dotanphu.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dotanphu.foodapp.activity.MealActivity
import com.dotanphu.foodapp.adapter.FavoritesMealsAdapter
import com.dotanphu.foodapp.databinding.FragmentSearchBinding
import com.dotanphu.foodapp.fragments.HomeFragment.Companion.MEAL_ID
import com.dotanphu.foodapp.fragments.HomeFragment.Companion.MEAL_NAME
import com.dotanphu.foodapp.fragments.HomeFragment.Companion.MEAL_THUMB
import com.dotanphu.foodapp.vm.SearchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchRVAdapter: FavoritesMealsAdapter
    private var mealId = ""
    private var mealStr = ""
    private var mealThub = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchRVAdapter = FavoritesMealsAdapter()
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        viewModel.searchMealDetail(searchQuery = String())
        observeSearchedMealsLiveData()
        onSearchClick()

        var searchJob: Job? = null
        binding.edtSearch.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMealDetail(searchQuery.toString())
            }
        }
    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner, Observer { mealList ->
            searchRVAdapter.differ.submitList(mealList)
        })
    }

    private fun onSearchClick() {
        searchRVAdapter.onItemClick = { category ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, category.idMeal)
            intent.putExtra(MEAL_NAME, category.strMeal)
            intent.putExtra(MEAL_THUMB, category.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        searchRVAdapter = FavoritesMealsAdapter()
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchRVAdapter
        }
    }
}