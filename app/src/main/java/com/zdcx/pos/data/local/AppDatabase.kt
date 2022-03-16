package com.zdcx.pos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zdcx.pos.data.dto.recipes.User
import com.zdcx.pos.data.local.dao.UserDao

@Database(entities = [User::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    //dao
    abstract fun userDao(): UserDao


    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "pos.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}