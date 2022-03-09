package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {
    private lateinit var sqLiteHelper: SQLiteHelper
    private var recyclerViewAdapter: UserRecyclerViewAdapter? = null
    private lateinit var  recyclerView :RecyclerView
    private lateinit var btnAllUsers :Button
    private lateinit var btnAllTransaction:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAllUsers = findViewById(R.id.btn_all_users)
        btnAllTransaction = findViewById(R.id.btn_all_transactions)



        btnAllUsers.setOnClickListener{
            val intent = Intent(this , UsersActivity::class.java)
            startActivity(intent)
        }

        btnAllTransaction.setOnClickListener{
            val intent = Intent(this , TransactionsActivity::class.java)
            startActivity(intent)
        }

    }



}