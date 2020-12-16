package com.example.pr9

import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder


class InputOperations {
    private lateinit var name: String
    private lateinit var type: String
    private lateinit var area: String
    private lateinit var subPrice: String

    fun inputCheck(activity:WorkWithOperator) : String? {

            name = activity.findViewById<EditText>(R.id.input_name).text.toString()
            type = activity.findViewById<EditText>(R.id.input_type).text.toString()
            area = activity.findViewById<EditText>(R.id.input_area).text.toString()
            subPrice = activity.findViewById<EditText>(R.id.input_subPrice).text.toString()

            var errors = StringBuilder()

            errors.append(checkName())
            errors.append(checkType())
            errors.append(checkArea())
            errors.append(checkSubPrice())

        return if(errors.isNullOrEmpty())
            null
        else
            errors.toString()
    }

    private fun checkName():String?{
        return if (Regex(pattern = "^(\\d*\\w*\\d*)+$").matches(name.toLowerCase())) null
            else "\nНеправильно заполнено имя"
    }
    private fun checkType():String? {
        return if (Regex(pattern = "^кабель|интернет|спутник|cable|internet|satellite$").matches(type.toLowerCase())) null
            else "\nНеправильно заполнено тип"
    }
    private fun checkArea():String?{
        return if (Regex(pattern = "^\\d{1,8}$").matches(area.toString())) null
            else "\nНеправильно заполнена площадь"
    }
    private fun checkSubPrice():String?{
        return if (Regex(pattern = "^\\d{1,5}$").matches(subPrice.toString())) null
            else "\nНеправильно заполнена цена подписки"
    }
}