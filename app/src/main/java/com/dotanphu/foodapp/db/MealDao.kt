package com.dotanphu.foodapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dotanphu.foodapp.model.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformaiton")
    fun getAllMeals(): LiveData<List<Meal>>
}