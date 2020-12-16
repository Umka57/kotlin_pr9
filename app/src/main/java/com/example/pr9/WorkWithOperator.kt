package com.example.pr9

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorkWithOperator : AppCompatActivity() {
    val inputImage = findViewById<ImageView>(R.id.set_photo)
    val inputName = findViewById<EditText>(R.id.input_name)
    val inputType = findViewById<EditText>(R.id.input_name)
    val inputArea = findViewById<EditText>(R.id.input_name)
    val inputSubPrice = findViewById<EditText>(R.id.input_name)

    val buttonDelete = findViewById<Button>(R.id.deleteBtn)
    val buttonCancel = findViewById<Button>(R.id.button_cancel)
    val buttonEnter = findViewById<Button>(R.id.button_enter)

    private var btnCancelClicked:Boolean = false
    private var bitmap: Bitmap? = null

    lateinit var operatordb:OperatorDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_with_operator)
        val operator = intent.getSerializableExtra("OPERATOR") as OperatorDB
        val mode = intent.getStringExtra("MODE")
        inputImage.setImageBitmap(operator.image)
        inputName.setText(operator.name)
        inputType.setText(operator.type)
        inputArea.setText(operator.area.toString())
        inputSubPrice.setText(operator.subPrice.toString())

        operatordb = Room.databaseBuilder(applicationContext,OperatorDatabase::class.java,"Operator").build()

        if (mode == "add") {
            buttonDelete.visibility = View.GONE
        }

        buttonEnter.setOnClickListener {
            insertItem(mode!!)
        }

        buttonCancel.setOnClickListener {

        }

        buttonDelete.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val operators:List<OperatorDB>? = null
                withContext(Dispatchers.IO){
                    operatordb.operatorDao().delete(operator)
                }
            }
        }

        inputImage.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.resolveActivity(packageManager)
            startActivityForResult(intent,GET_PHOTO)
        }
    }
     companion object{
       const val GET_PHOTO = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if((requestCode== GET_PHOTO)&&(resultCode== Activity.RESULT_OK)){
            if(data!=null){
                bitmap = data.extras?.get("data") as Bitmap
                inputImage.setImageBitmap(bitmap)
            }
        }
    }

    fun insertItem(mode:String){
        val input = InputOperations()

        val errors = input.inputCheck(this)
        when {
            errors.isNullOrEmpty() -> {
                val newItem = OperatorDB(0,bitmap,inputName.text.toString(),inputType.text.toString(),inputArea.text.toString().toInt(),inputSubPrice.text.toString().toInt())
                when (mode) {
                    "add" -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            withContext(Dispatchers.IO){
                                operatordb.operatorDao().insert(newItem)
                            }
                            finish()
                        }
                        Toast.makeText(applicationContext, "Оператор успешно изменен", Toast.LENGTH_SHORT).show()
                    }
                    "edit" -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            withContext(Dispatchers.IO){
                                operatordb.operatorDao().update(newItem)
                            }
                            finish()
                        }
                        Toast.makeText(applicationContext, "Оператор успешно изменен", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            else -> Toast.makeText(applicationContext, errors.toString(), Toast.LENGTH_SHORT).show()
        }


    }
}