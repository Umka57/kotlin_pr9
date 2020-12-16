package com.example.pr9

import android.graphics.Bitmap
import java.io.Serializable

data class OperatorItem(
    var imageResource: Bitmap?,
    var name:String,
    var type:String,
    var area: Int,
    var subPrice: Int
):Serializable