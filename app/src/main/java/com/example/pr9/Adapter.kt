package com.example.pr9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.operator_card.view.*

class Adapter(
    private val operatorsList: List<OperatorItem>,
    private val listener: OnItemClickListener
    ) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.operator_card,
        parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = operatorsList[position]

        holder.imageV.setImageResource(currentItem.imageResource)
        holder.nameTV.text = currentItem.name
        holder.typeTV.text = currentItem.type
        holder.areaTV.text = currentItem.area.toString()
        holder.subPriceTV.text = currentItem.subPrice.toString()
    }

    override fun getItemCount() = operatorsList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        //Image view with logo
        val imageV: ImageView = itemView.image_view
        //TextView with operators name
        val nameTV: TextView = itemView.text_view_name
        //TextView with operators type(Internet,cable,satelite)
        val typeTV: TextView = itemView.text_view_type
        //TextView with operators area
        val areaTV: TextView = itemView.text_view_area
        //TextView with subPrice
        val subPriceTV: TextView = itemView.text_view_subPrice

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}