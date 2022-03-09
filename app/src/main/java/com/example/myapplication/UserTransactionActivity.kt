package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserTransactionActivity : AppCompatActivity() {
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var btnSend : Button
    private lateinit var btnAllUsers :Button
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvCurrentBalance :TextView
    private lateinit var tvIban :TextView

    private lateinit var transferValue: EditText
    private lateinit var spinner:Spinner
    private lateinit var recieverEmail: String
    private var currentBalanceOfReceiver : Int = 0
   private lateinit var reciver:UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_transaction)
        sqLiteHelper = SQLiteHelper(this)

        var intent = intent

        val userId = intent.getStringExtra("userId")
        val name = intent.getStringExtra("userName")
        val email=  intent.getStringExtra("userEmail")
        val ibanCode = intent.getStringExtra("userIban")
        val currentBalance = intent.getStringExtra("userCurrentBalance")



        tvName = findViewById(R.id.username_transaction)
        tvEmail = findViewById(R.id.user_email_transaction)
        tvCurrentBalance = findViewById(R.id.user_balance_transacion)
        tvIban = findViewById(R.id.iban_code)

        transferValue = findViewById(R.id.et_numer_transaction)
        spinner = findViewById<Spinner>(R.id.spinner)
        btnSend = findViewById(R.id.btn_send)

        tvName.text = name
        tvEmail.text = email
        tvIban.text = ibanCode

        tvCurrentBalance.text = currentBalance

        val listUsers:ArrayList<UserModel> = sqLiteHelper.getAllUsers()
        var listSpinner  :ArrayList<String> = ArrayList()
        var listPositions  :ArrayList<Int> = ArrayList()

        var i = 0
        for( user in listUsers){
            if( user.id != userId?.toInt()) {
                listSpinner.add( user.name )
                listPositions.add(i)
            }
            i++
        }
        val arrAdapterSpinner = ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,listSpinner)

        spinner.adapter = arrAdapterSpinner

        spinner.onItemSelectedListener =object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                reciver = listUsers[listPositions[position]]

                currentBalanceOfReceiver = listUsers[listPositions[position]].currentBalance
               // Toast.makeText(this@UserTransactionActivity, "send ${transferValue.text}" +listSpinner[position],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        btnSend.setOnClickListener {

            val calendar = Calendar.getInstance()
            val simpleDateFormat = SimpleDateFormat("dd.LLLL.yyyy HH:mm aaa ")
            val dateTime = simpleDateFormat.format(calendar.time).toString()
            var flagSuccess = true

            if (transferValue.text.isNotEmpty()) {
                    if (transferValue.text.toString().toInt() > tvCurrentBalance.text.toString().toInt()) {
                        Toast.makeText(
                            this@UserTransactionActivity,
                            "Current Balance don't enough",
                            Toast.LENGTH_SHORT
                        ).show()
                        flagSuccess = false
                    } else {

                        val valueOfSender: Int =
                            tvCurrentBalance.text.toString().toInt() - transferValue.text.toString()
                                .toInt()
                        val valueOfReceiver: Int =
                            transferValue.text.toString().toInt() + currentBalanceOfReceiver

                        val success1 = sqLiteHelper.updateBalance(userId, valueOfSender)
                        val success2 =
                            sqLiteHelper.updateBalance(reciver.id.toString(), valueOfReceiver)



                        val response = sqLiteHelper.insertTransaction(
                            name.toString(),
                            reciver.name,
                            transferValue.text.toString(),"Success",
                            dateTime
                        )

                        if (response > -1) {
                            Toast.makeText(this@UserTransactionActivity, "Transfer money is completed", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                this@UserTransactionActivity,
                                "transaction fail",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        // Toast.makeText(this@UserTransactionActivity, "transaction Successfully current balance is $valueOfSender", Toast.LENGTH_SHORT).show()
                        tvCurrentBalance.text = valueOfSender.toString()
                    }

            }else{
                Toast.makeText(
                    this@UserTransactionActivity,
                    "Transfer money is empty",
                    Toast.LENGTH_SHORT
                ).show()
                flagSuccess = false
            }
            if( !flagSuccess ){

                val response = sqLiteHelper.insertTransaction(
                    name.toString(),
                    reciver.name,
                    transferValue.text.toString(),"Fail",
                    dateTime
                )

            }
            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
        }
        }


























    }


