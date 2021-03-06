
package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adaptor.UserRecyclerViewAdapter
import com.example.myapplication.database.SQLiteHelper


class UsersActivity : AppCompatActivity() {
    private lateinit var sqLiteHelper: SQLiteHelper
    private var adapter: UserRecyclerViewAdapter? = null
    private lateinit var  recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_activity)

        recyclerView = findViewById(R.id.recyclerview_users_activity)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserRecyclerViewAdapter()
        recyclerView.adapter = adapter

        sqLiteHelper = SQLiteHelper(this)


        val users = sqLiteHelper.getAllUsers()
        adapter?.addItem(users)





    }

}