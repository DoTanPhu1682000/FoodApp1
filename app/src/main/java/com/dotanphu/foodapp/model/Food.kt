package com.dotanphu.foodapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("food")
data class Food(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int,
    @ColumnInfo(name = "_tittle")
    val tittle: String,
    @ColumnInfo(name = "_pic")
    val pic: String,
    @ColumnInfo(name = "_price")
    val price: Double
)
