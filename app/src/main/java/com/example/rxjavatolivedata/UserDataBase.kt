package com.example.rxjavatolivedata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [model::class],version = 1,exportSchema = false)
abstract class UserDataBase : RoomDatabase() {

    abstract fun getUsersDao() : userDB

    companion object {

        @Volatile private var INSTANCE: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UserDataBase::class.java, "Sample.db")
                .build()
    }
}