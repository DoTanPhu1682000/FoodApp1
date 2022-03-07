package com.dotanphu.foodapp.retrofit

import com.dotanphu.foodapp.model.CategoryList
import com.dotanphu.foodapp.model.MealsByCategoryList
import com.dotanphu.foodapp.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {

    @GET("filter.php?")
    fun getPopularItem(@Query("c") categoryName: String): Call<MealsByCategoryList>

    @GET("lookup.php?")
    fun getMealsById(@Query("i") id: String): Call<MealList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealsByCategoryList>
}