package com.dotanphu.foodapp.vm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dotanphu.foodapp.model.Meal
import com.dotanphu.foodapp.model.MealList
import com.dotanphu.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private var searchedMealLiveData = MutableLiveData<List<Meal>>()


    fun searchMealDetail(searchQuery: String) {
        RetrofitInstance.foodApi.searchMeals(searchQuery).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    searchedMealLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("TAG", t.message.toString())
            }

        })
    }

    fun observeSearchedMealsLiveData(): LiveData<List<Meal>> = searchedMealLiveData
}