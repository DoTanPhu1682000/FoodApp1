package com.dotanphu.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dotanphu.foodapp.databinding.ItemMealFavoritesBinding
import com.dotanphu.foodapp.db.MealDatabase
import com.dotanphu.foodapp.model.Meal

class FavoritesMealsAdapter : RecyclerView.Adapter<FavoritesMealsAdapter.ViewHolder>() {
    private var favoriteMeals: List<MealDatabase> = ArrayList()

    inner class ViewHolder(val binding: ItemMealFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun getMelaByPosition(position: Int):MealDatabase{
        return favoriteMeals[position]
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMealFavoritesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgFavMeal)
        holder.binding.tvFavMealName.text = meal.strMeal
    }

    override fun getItemCount() = differ.currentList.size
}