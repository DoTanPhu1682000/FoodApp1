package com.dotanphu.foodapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dotanphu.foodapp.databinding.ActivityHomeBinding
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.dotanphu.foodapp.adapter.CategoryAdapter
import com.dotanphu.foodapp.model.Category
import com.dotanphu.foodapp.model.CategoryRespone
import com.dotanphu.foodapp.retrofit.CategoryClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.ArrayList


class HomeActivity : AppCompatActivity() {
    //    private lateinit var adapter: FoodAdapter
    private lateinit var adapter: CategoryAdapter
    private lateinit var binding: ActivityHomeBinding

    //    var food = ArrayList<Food>()
    var category = ArrayList<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //temporary data
//        food.add(Food(1,"FastFood","https://cdn-icons-png.flaticon.com/512/2922/2922037.png",8.6))
//        food.add(Food(2,"Noodles","https://cdn-icons-png.flaticon.com/512/2718/2718224.png",8.6))
//        food.add(Food(3,"FastFood","https://cdn-icons-png.flaticon.com/512/2922/2922037.png",8.6))
//        food.add(Food(4,"Noodles","https://cdn-icons-png.flaticon.com/512/2718/2718224.png",8.6))
//        food.add(Food(5,"FastFood","https://cdn-icons-png.flaticon.com/512/2922/2922037.png",8.6))
//        food.add(Food(6,"Noodles","https://cdn-icons-png.flaticon.com/512/2718/2718224.png",8.6))


//        adapter = FoodAdapter(food, this)
        adapter = CategoryAdapter(category, this)
        binding.rvCategory.adapter = adapter
        binding.rvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getFoodVersion()


        binding.btnHome.setOnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.btnShopping.setOnClickListener {
            intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

    }

    fun getFoodVersion() {
        lifecycleScope.launch(Dispatchers.IO) {
            var reponse: Response<CategoryRespone> = CategoryClient().getAllCategory().execute()
            if (reponse.isSuccessful) {
                reponse.body()?.category?.let {
                    category.addAll(it)
                }
                withContext(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}