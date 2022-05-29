package com.dotanphu.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dotanphu.foodapp.R
import com.dotanphu.foodapp.activity.CategoryMealsActivity
import com.dotanphu.foodapp.activity.MainActivity
import com.dotanphu.foodapp.activity.MealActivity
import com.dotanphu.foodapp.adapter.CategoriesAdapter
import com.dotanphu.foodapp.adapter.PopularAdapter
import com.dotanphu.foodapp.databinding.FragmentHomeBinding
import com.dotanphu.foodapp.fragments.bottomsheet.MealBottomSheetFragment
import com.dotanphu.foodapp.model.MealsByCategory
import com.dotanphu.foodapp.vm.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var categoriesAdapter: CategoriesAdapter


    companion object {
        const val MEAL_ID = "com.dotanphu.foodapp.fragments.idMeal"
        const val MEAL_NAME = "com.dotanphu.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.dotanphu.foodapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.dotanphu.foodapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
        viewModel = (activity as MainActivity).viewModel
        popularAdapter = PopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularMeals()
        viewModel.getMealsByCategory()
        observePopularItemLiveData()
        onPopularItemClick()


        prepareCategoriesRecycleView()
        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()

        onPopularItemLongClick()

        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularAdapter.onLongItemClick = { meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecycleView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categories.forEach { category ->
                categoriesAdapter.setCategoryList(categories)

            }

        })
    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observePopularItemLiveData() {
        viewModel.observeMeal().observe(viewLifecycleOwner,
            { mealList ->
                popularAdapter.setMealList(mealsByCategoryList = mealList as ArrayList<MealsByCategory>)
            })
    }


    private fun preparePopularMeals() {
        binding.rvPopular.apply {
            adapter = popularAdapter
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        }
    }
}