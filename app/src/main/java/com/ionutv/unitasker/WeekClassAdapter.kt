package com.ionutv.unitasker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ionutv.unitasker.DataClasses.Classes
import kotlinx.android.synthetic.main.item_classes.view.*

class WeekClassAdapter(var classes:ArrayList<Classes>): RecyclerView.Adapter<WeekClassAdapter.ViewHolder> (){


    override fun getItemCount(): Int {
        return classes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(

        LayoutInflater.from(parent.context).inflate(R.layout.item_classes,parent,false)

    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(classes[position])

    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view){

        private val clas = view.tvclasses
        private val room = view.tvroom

        fun bind( userClass: Classes){
            clas.text= userClass.name
            room.text= userClass.room
        }
    }
}