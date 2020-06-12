package com.example.rxjavatolivedata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface userDB {

    //get all users
    @Query("SELECT * FROM userTable ORDER BY userID ASC")
    fun getAllUsers() : Flowable<List<model>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(model: model) : Completable

    @Query("DELETE FROM userTable WHERE userID NOT IN (SELECT MIN(userID) FROM userTable GROUP BY name, username)")
    fun removeDuplicates() : Completable
}