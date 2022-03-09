package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adaptor.TransactionAdapter
import com.example.myapplication.database.SQLiteHelper

class TransactionsActivity : AppCompatActivity() {
    private lateinit var sqLiteHelper: SQLiteHelper
    private var adapter: TransactionAdapter? = null
    private lateinit var  recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        recyclerView = findViewById(R.id.recyclerview_transactions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TransactionAdapter()
        recyclerView.adapter = adapter

        sqLiteHelper = SQLiteHelper(this)



        val transactions = sqLiteHelper.getAllTransactions()
        adapter?.addItem(transactions)

    }
}