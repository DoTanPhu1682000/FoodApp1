package com.dotanphu.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dotanphu.foodapp.databinding.ItemListCategoryBinding
import com.dotanphu.foodapp.model.Category

class CategoryAdapter(private var listCategory: List<Category>, private var context: Context) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        return ViewHolder(
            ItemListCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(listCategory[position])
    }

    override fun getItemCount() = listCategory.size

    class ViewHolder(private var binding: ItemListCategoryBinding, private var context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvIdCategory.text = category.idCategory
            binding.tvStrCategory.text = category.strCategory
            binding.tvStrCategoryDescription.text = category.strCategoryDescription
            Glide.with(context).load(category.strCategoryThumb).into(binding.tvStrCategoryThumb)
        }
    }
}