package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UserRecyclerViewAdapter : RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder>() {
    private var userList:ArrayList<UserModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= UserViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.ly_user_item,parent,false)
    )


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bindView(user)

    }

    fun addItem( users : ArrayList<UserModel>){
        this.userList = users
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(var view :View):RecyclerView.ViewHolder(view){
        private var btnTransaction = view.findViewById<Button>(R.id.btn_transaction)
        private var name = view.findViewById<TextView>(R.id.name)
        private var email = view.findViewById<TextView>(R.id.email)
        private var currentBalance = view.findViewById<TextView>(R.id.balance)

        fun bindView(user :UserModel){
            name.text = user.name
            email.text = user.email
            currentBalance.text = user.currentBalance.toString() +"$"
            btnTransaction.setOnClickListener{
                val intent = Intent(view.context,UserTransactionActivity::class.java)
                intent.putExtra("userId" ,user.id.toString() )
                intent.putExtra("userEmail" ,user.email)
                intent.putExtra("userName" ,user.name)
                intent.putExtra("userIban" ,user.ibanCode)
                intent.putExtra("userCurrentBalance" ,user.currentBalance.toString())

                view.context.startActivity(intent)

            }
        }

    }


}