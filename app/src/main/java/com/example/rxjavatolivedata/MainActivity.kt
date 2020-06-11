package com.example.rxjavatolivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var viewmodel: userViewModel? = null
    lateinit var list: MutableList<model>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel = ViewModelProvider(this)[userViewModel::class.java]
        PopulateDB()  //Inseting Data into db
        getAllUsers() // Getting Data from db

    }

    private fun PopulateDB() {
        list = ArrayList()
        list.add(model("David", "Carlos"))
        list.add(model("Monatana", "Jeff"))
        list.add(model("Loca", "Carlos"))
        list.add(model("John", "Beyla"))
        list.add(model("David", "Borwa"))
        list.map {
            viewmodel!!.insertUsers(it)
        }
    }

    private fun getAllUsers() {
        viewmodel!!.getAllUsers().observe(this, Observer {
            var arrayAdapter = CustomAdapter(this, it)
            listview.adapter = arrayAdapter
        })
    }


}