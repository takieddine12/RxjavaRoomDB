package com.example.rxjavatolivedata

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapter(context: Context, var list: List<model>) : ArrayAdapter<model>(context, 0) {

    override fun getCount(): Int {
        return list.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = LayoutInflater.from(context).inflate(R.layout.userlayout, parent, false)
        var text_name = view.findViewById<TextView>(R.id.name)
        var text_username = view.findViewById<TextView>(R.id.username)
        text_name.text = list[position].name
        text_username.text = list[position].username
        view.tag = convertView

        return view
    }
}