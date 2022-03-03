package com.dotanphu.foodapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dotanphu.foodapp.model.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun getFoodDao(): FoodDao

    companion object {
        private var INSTANCE: FoodDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): FoodDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    ctx.applicationContext,
                    FoodDatabase::class.java,
                    "food.db"
                ).fallbackToDestructiveMigration()
                    .addCallback(object :RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            insertFakeData()
                        }

                        private fun insertFakeData() {
                            val foodDao = INSTANCE!!.getFoodDao()
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                        }

                        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                            super.onDestructiveMigration(db)
                        }
                    })
                    .build()
            }
            return INSTANCE!!
        }
    }
}