package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.model.TransactionModel
import com.example.myapplication.model.UserModel
import java.lang.Exception

class SQLiteHelper(context: Context):SQLiteOpenHelper(context,
    DATABASE_NAME ,null,
    DATABASE_VERSION
) {
    companion object{
        private const val DATABASE_NAME = "bank.db"
        private const val DATABASE_VERSION = 1
        private const val TB_USER = "tb_user"
        private const val ID = "_id"
        private const val NAME = "name"
        private const val EMAIL ="email"
        private const val IBAN_CODE = "iban_code"

        private const val CURRENT_BALANCE = "current_balance"

        private const val TB_TRANSACTION = "transaction_table"
        private const val TRANSACTION_ID = "transaction_id"

        private const val FROM = "_from"
        private const val TO = "_to"
        private const val AMOUNT = "_amount"
        private const val STATUS = "_status"

        private const val DATE = "_date"


    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE " + TB_USER + "(" +
                ID + " INTEGER PRIMARY KEY , " +
                NAME + " TEXT,"+
                EMAIL + " TEXT," +
                IBAN_CODE + " TEXT," +
                CURRENT_BALANCE + " TEXT" + ")")


        db?.execSQL("CREATE TABLE " + TB_TRANSACTION + "(" +
                TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                FROM + " TEXT," +
                TO + " TEXT," +
                AMOUNT  + " TEXT," +
                STATUS  + " TEXT," +
                DATE + " TEXT" + ")")



                // insert into table users
        db?.execSQL("insert into $TB_USER values(20,'Ahmed Maher', 'ahmedmaher@gmail.com','EG800002000156789012345180002', '180000')")
        db?.execSQL("insert into $TB_USER values(15,'Ramy ali', 'ramy@gmail.com','EG800002000156789012345180003', '50000')")
        db?.execSQL("insert into $TB_USER values(18,'Omar zaitoun', 'oamar@gmail.com','EG800002000156789012345180004', '10000')")
        db?.execSQL("insert into $TB_USER values(5,'Ali magdy', 'ali@gmail.com','EG800002000156789012345180005',  '80000')")
        db?.execSQL("insert into $TB_USER values(4,'Sara ahmed', 'sara@gmail.com','EG800002000156789012345180006',  '75000')")
        db?.execSQL("insert into $TB_USER values(3,'Nabil hassan', 'nabil@gmail.com','EG800002000156789012345180007',  '65000')")
        db?.execSQL("insert into $TB_USER values(12,'El deek el Roomy', 'eldeekelroomy@gmail.com','EG800002000156789012345180008',  '45000')")
        db?.execSQL("insert into $TB_USER values(9,'Esraa mohammed', 'esraa@gmail.com','EG800002000156789012345180009',  '25000')")
        db?.execSQL("insert into $TB_USER values(2,'Walaa maher', 'walaa@gmail.com' ,'EG800002000156789012345180010', '105000')")
        db?.execSQL("insert into $TB_USER values(1,'Kamilia abdallah', 'kamilia@gmail.com','EG800002000156789012345180011',  '99000')")





    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TB_USER")
        db!!.execSQL("DROP TABLE IF EXISTS $TB_TRANSACTION")
        onCreate(db)
    }

    fun insertUser(user : UserModel):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID , user.id)
        contentValues.put(NAME ,user.name)
        contentValues.put(EMAIL , user.email)
        contentValues.put(CURRENT_BALANCE,user.currentBalance)
        val success = db.insert(TB_USER , null , contentValues)
        db.close()
        return success
    }

    fun insertTransaction(from: String, to: String, amount: String, status: String , date:String):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(FROM ,from)
        contentValues.put(TO , to)
        contentValues.put(AMOUNT, "$amount$")
        contentValues.put(DATE,date)
        contentValues.put(STATUS,status)
        val success = db.insert(TB_TRANSACTION , null , contentValues)
        db.close()
        return success
    }

    fun getAllUsers():ArrayList<UserModel>{
        val db = this.readableDatabase

        val userList: ArrayList<UserModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TB_USER"

        var cursor: Cursor? =null

        try {
            cursor= db.rawQuery(selectQuery,null)

        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id :Int
        var name: String
        var email:String
        var ibanCode:String
        var currentBalance:Int

        if( cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                ibanCode = cursor.getString(cursor.getColumnIndexOrThrow("iban_code"))
                currentBalance = cursor.getInt(cursor.getColumnIndexOrThrow("current_balance"))
                val user = UserModel(id,name, email,ibanCode, currentBalance)
                userList.add(user)

            }while (cursor.moveToNext())
        }
        return userList


    }
    fun getAllTransactions():ArrayList<TransactionModel>{
        val db = this.readableDatabase

        val transactionList: ArrayList<TransactionModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TB_TRANSACTION"

        val cursor: Cursor?

        try {
            cursor= db.rawQuery(selectQuery,null)


        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id :Int
        var from: String
        var to:String
        var amount:String
        var date:String
        var status:String

        if( cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndexOrThrow(TRANSACTION_ID))
                from = cursor.getString(cursor.getColumnIndexOrThrow(FROM))
                to = cursor.getString(cursor.getColumnIndexOrThrow(TO))
                amount = cursor.getString(cursor.getColumnIndexOrThrow(AMOUNT))
                date = cursor.getString(cursor.getColumnIndexOrThrow(DATE))
                status = cursor.getString(cursor.getColumnIndexOrThrow(STATUS))
                val transaction = TransactionModel(id ,from, to, amount ,status,date)
                transactionList.add(transaction)

            }while (cursor.moveToNext())
        }
        return transactionList


    }
    fun updateBalance( id :String?  ,currentBalance: Int?){
        val db = this.writableDatabase
        db.execSQL("update tb_user set current_balance = $currentBalance where _id = $id")

    }

}