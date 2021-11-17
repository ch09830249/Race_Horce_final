package com.example.horcerunning_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.horcerunning_final.database.HistoryDatabase
import com.example.horcerunning_final.database.Record
import com.example.horcerunning_final.database.RecordDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var db: HistoryDatabase
    lateinit var userDao: RecordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Establish DB
        CoroutineScope(Dispatchers.IO).launch {
            db = Room.databaseBuilder(applicationContext,
                HistoryDatabase::class.java, "database-name"
            ).build()
            userDao = db.getDao()
            val ex1 = Record(1, "apple", 150, "banana", 300, 100000)
            userDao.insertData(ex1)
        }
    }
}