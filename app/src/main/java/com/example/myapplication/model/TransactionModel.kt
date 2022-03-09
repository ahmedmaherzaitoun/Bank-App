package com.example.myapplication.model

data class TransactionModel (
    val transaction_id:Int = getAutoId() ,
    val from:String,
    val to: String,
    val amount:String,
    val status: String,
    val date:String) {
    companion object {
        fun getAutoId(): Int {
            val random = java.util.Random()
            return random.nextInt(10000000)
        }


    }
}