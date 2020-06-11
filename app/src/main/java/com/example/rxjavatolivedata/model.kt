package com.example.rxjavatolivedata

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
@Entity(tableName = "userTable")
class model(
    var name : String? = null,
    var username : String? = null,
    @PrimaryKey(autoGenerate = true) @NotNull var userID : Int? = null
) {
}