package com.dotanphu.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dotanphu.foodapp.databinding.ItemListPopularBinding
import com.dotanphu.foodapp.model.MealsByCategory

class PopularAdapter() : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    var onLongItemClick: ((MealsByCategory) -> Unit)? = null
    private var mealsList = ArrayList<MealsByCategory>()
    lateinit var onItemClick: ((MealsByCategory) -> Unit)

    fun setMealList(mealsByCategoryList: ArrayList<MealsByCategory>) {
        this.mealsList = mealsByCategoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.ViewHolder {
        return ViewHolder(
            ItemListPopularBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(imgPopularMeal)
        }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(mealsList[position])
            true
        }
    }

    override fun getItemCount() = mealsList.size

    class ViewHolder(var binding: ItemListPopularBinding) :
        RecyclerView.ViewHolder(binding.root)
}