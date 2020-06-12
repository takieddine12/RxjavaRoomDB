package com.example.rxjavatolivedata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
@Entity(tableName = "userTable")
class model(
    @ColumnInfo(name= "name") var name : String? = null,
    @ColumnInfo(name= "username") var username : String? = null,
    @PrimaryKey(autoGenerate = true) @NotNull var userID : Int? = null
) {
}