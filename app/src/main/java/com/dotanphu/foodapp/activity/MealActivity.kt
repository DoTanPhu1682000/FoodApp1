package com.dotanphu.foodapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dotanphu.foodapp.databinding.ActivityMealDetailBinding
import com.dotanphu.foodapp.fragments.HomeFragment
import com.dotanphu.foodapp.model.Meal
import com.dotanphu.foodapp.vm.MealViewModel


class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealMvvm: MealViewModel


    private lateinit var binding: ActivityMealDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]
        getMealInformationFromIntent()
        setInformationInView()

        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()



    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                val meal = t
                binding.tvCategoryInfo.text = "Category: ${meal!!.strCategory}"
                binding.tvAreaInfo.text = "Area: ${meal.strArea}"
                binding.tvContent.text = meal.strInstructions
            }

        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
    }


    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
}