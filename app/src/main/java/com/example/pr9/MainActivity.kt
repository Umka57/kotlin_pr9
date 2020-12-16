package com.example.pr9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val operatorsList = ArrayList<OperatorDB>()

    lateinit var recyclerViewManager:LinearLayoutManager

    lateinit var operatordb:OperatorDatabase

    val buttonAdd = findViewById<Button>(R.id.addBtn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewManager = LinearLayoutManager(this)

        operatordb = Room.databaseBuilder(applicationContext,OperatorDatabase::class.java,"Operator").build()

        buttonAdd.setOnClickListener {
            val intent = Intent(this,WorkWithOperator::class.java)
            intent.putExtra("MODE","add")
            startActivity(intent)
        }

    }

    override fun onStart(){
        super.onStart()
        CoroutineScope(Dispatchers.Main).launch {
            val operators:List<OperatorDB>? = null
            withContext(Dispatchers.IO){
                val operators = operatordb.operatorDao().getAll()
            }
            val operatorsAdapter = OperatorAdapter(operators!!,this@MainActivity)
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_operators)
            recyclerView.layoutManager = recyclerViewManager
            recyclerView.adapter = operatorsAdapter
        }
    }
}