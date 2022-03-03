package com.dotanphu.foodapp.database


import androidx.lifecycle.LiveData
import androidx.room.*
import com.dotanphu.foodapp.model.Food


@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFood(food: Food)

    @Update
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("SELECT * FROM food")
    suspend fun getAllFood(): List<Food>

    @Query("SELECT * FROM food")
    fun getAllFoodWithLiveData(): LiveData<List<Food>>
}