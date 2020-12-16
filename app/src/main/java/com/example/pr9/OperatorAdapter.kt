package com.example.pr9

import android.content.Intent
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.operator_card.view.*

class OperatorAdapter(val operatorsList: List<OperatorDB>,val mainActivity:MainActivity) : RecyclerView.Adapter<OperatorAdapter.OperatorHolder>() {

    class OperatorHolder(val view: View): RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : OperatorAdapter.OperatorHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.operator_card,parent,false)
        return OperatorHolder(view)
    }

    override fun onBindViewHolder(holder: OperatorAdapter.OperatorHolder, position: Int) {
        val currentItem = operatorsList[position]
        holder.view.findViewById<ImageView>(R.id.image_view).setImageBitmap(currentItem.image)
        holder.view.findViewById<TextView>(R.id.text_view_name).text = currentItem.name
        holder.view.findViewById<TextView>(R.id.text_view_type).text = currentItem.type
        holder.view.findViewById<TextView>(R.id.text_view_area).text = currentItem.area.toString()
        holder.view.findViewById<TextView>(R.id.text_view_subPrice).text = currentItem.subPrice.toString()
        holder.view.setOnClickListener{
            val intent = Intent(mainActivity,WorkWithOperator::class.java)
            intent.putExtra("OPERATOR",operatorsList[position])
            intent.putExtra("MODE","edit")
            mainActivity.startActivity(intent)
        }
    }

    override fun getItemCount() = operatorsList.size

}