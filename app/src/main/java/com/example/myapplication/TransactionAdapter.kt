package com.example.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter :RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    private var transactionList:ArrayList<TransactionModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransactionViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.ly_user_transaction,parent,false)
    )


    override fun onBindViewHolder(holder: TransactionAdapter.TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]
        holder.bindView(transaction)
    }
    fun addItem( transactions : ArrayList<TransactionModel>){
        this.transactionList = transactions
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {

          return transactionList.size
    }

    class TransactionViewHolder(view :View):RecyclerView.ViewHolder(view){
        private var from = view.findViewById<TextView>(R.id.transaction_from)
        private var to = view.findViewById<TextView>(R.id.transaction_to)
        private var date = view.findViewById<TextView>(R.id.transaction_date)
        private var amount = view.findViewById<TextView>(R.id.transaction_amount)
        private var status = view.findViewById<TextView>(R.id.status)


        fun bindView(transaction: TransactionModel){
            from.text = transaction.from
            to.text = transaction.to
            amount.text = transaction.amount
            date.text = transaction.date
            status.text = transaction.status
            if( transaction.status =="Success"){
                status.setTextColor(Color.parseColor("#4CAF50"))
            }else{
                status.setTextColor(Color.parseColor("#FF5722"))
            }
        }


        }

}