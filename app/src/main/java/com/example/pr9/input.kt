package com.example.pr9

import android.widget.EditText
import android.widget.Toast
import java.lang.StringBuilder

class Input {
    fun inputChecker( inputName:String, inputType:String, inputArea:String,inputSubPrice:String) : OperatorItem {

        var item = OperatorItem("","","",0,0)
        var errors = StringBuilder()
            val name = inputName.toLowerCase()
            if (Regex(pattern = "^(\\d*\\w*\\d*)+$").matches(name)) {
                item.name = name
            } else
                errors.append("\nНеправильно заполнено имя")
        
            val type = inputType.toLowerCase()
            if (Regex(pattern = "^кабель|интернет|спутник|cable|internet|satellite$").matches(type)) {
                item.type = inputType
            } else
                errors.append("\nНеправильно заполнено тип")

            val area = inputArea.toString()
            if (Regex(pattern = "^\\d{1,8}$").matches(area)) {
                item.area = area.toInt()
            } else
                errors.append("\nНеправильно заполнена площадь")

            val sub = inputSubPrice.toString()
            if (Regex(pattern = "^\\d{1,5}$").matches(sub)) {
                item.subPrice = sub.toInt()
            } else
                errors.append("\nНеправильно заполнена цена подписки")
        if (errors.isNotEmpty()){
            item.name=""
            item.type=errors.toString()
        }
        return item
    }
}