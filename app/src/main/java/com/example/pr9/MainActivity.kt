package com.example.pr9

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Adapter.OnItemClickListener {
    private var btnCancelClicked:Boolean = false
    private var clickedItemPosition:Int = -1
    private lateinit var clickedItem : OperatorItem
    private val operatorsList = ArrayList<OperatorItem>()/// TODO: 07.12.2020 В будующем дописать взаимодействию с бд
    private val adapter = Adapter(operatorsList, this)
    val inputName = findViewById<EditText>(R.id.input_name)
    val inputType = findViewById<EditText>(R.id.input_name)
    val inputArea = findViewById<EditText>(R.id.input_name)
    val inputSubPrice = findViewById<EditText>(R.id.input_name)
    val inputForm = findViewById<LinearLayout>(R.id.input_form)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_operators.adapter = adapter
        recycler_operators.layoutManager = LinearLayoutManager(this)
        recycler_operators.setHasFixedSize(true)
        val buttonCancel = findViewById<Button>(R.id.button_cancel)
        buttonCancel.setOnClickListener{
            btnCancelClicked = true
        }
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        clickedItem = operatorsList[position]
        clickedItemPosition = position
        inputName.setText(clickedItem.name)
        inputType.setText(clickedItem.type)
        inputArea.setText(clickedItem.area.toString())
        inputSubPrice.setText(clickedItem.subPrice.toString())
        inputForm.visibility = View.VISIBLE
        var checker = true
        val index = clickedItemPosition
        while (checker){
            val newItem = Input().inputChecker(inputName.text.toString(),inputType.text.toString(),inputArea.text.toString(),inputSubPrice.text.toString())
            if(newItem.name!=""||btnCancelClicked){
                operatorsList.add(index,newItem)
                adapter.notifyItemInserted(index)
                checker=false
                btnCancelClicked = false
            } else Toast.makeText(applicationContext, newItem.type, Toast.LENGTH_SHORT).show()
        }
        adapter.notifyItemChanged(position)
    }

    fun insertItem(view: View){
        val index = operatorsList.size
        inputForm.visibility = View.VISIBLE
        var checker = true
        while (checker){
            val newItem = Input().inputChecker(inputName.text.toString(),inputType.text.toString(),inputArea.text.toString(),inputSubPrice.text.toString())
            if(newItem.name!=""||btnCancelClicked){
                operatorsList.add(index,newItem)
                adapter.notifyItemInserted(index)
                checker=false
                btnCancelClicked = false
            } else Toast.makeText(applicationContext, newItem.type, Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteItem(view: View){
        if (clickedItemPosition!=-1){
            operatorsList.removeAt(clickedItemPosition)
            adapter.notifyItemRemoved(clickedItemPosition)
        }
    }
}