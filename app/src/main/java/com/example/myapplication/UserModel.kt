package com.example.myapplication

import java.util.*
import kotlin.random.Random


data class UserModel(
    var id:Int = getAutoId() ,
    var name:String = "" ,
    var email:String = "" ,
    var ibanCode:String = "EG800002000156789012345180002",
    var currentBalance:Int = 0
    ){
    companion object{
        fun getAutoId():Int{
            val random = java.util.Random()
            return random.nextInt(1000000)
        }

    }

}
