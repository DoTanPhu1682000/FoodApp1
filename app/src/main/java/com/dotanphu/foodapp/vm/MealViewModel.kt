package com.dotanphu.foodapp.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dotanphu.foodapp.db.MealDatabase
import com.dotanphu.foodapp.model.Meal
import com.dotanphu.foodapp.model.MealList
import com.dotanphu.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MealViewModel(val mealDatabase: MealDatabase) : ViewModel() {
    private var mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id: String) {
        RetrofitInstance.foodApi.getMealsById(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailLiveData.value = response.body()!!.meals[0]
                } else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity", t.message.toString())
            }

        })
    }

    fun observerMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailLiveData
    }


    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insert(meal)
        }
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase
        }
    }
}
