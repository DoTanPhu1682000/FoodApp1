package com.dotanphu.foodapp.retrofit

import com.dotanphu.foodapp.model.CategoryRespone
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {
    @GET("categories.php")
    fun getAllCategory(): Call<CategoryRespone>
}