package com.ionutv.unitasker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ionutv.unitasker.dataClasses.Classes
import kotlinx.android.synthetic.main.item_classes.view.*


class WeekClassAdapter(var classes: ArrayList<Classes>, private val context: Context, private val week_viewed: String) :
    RecyclerView.Adapter<WeekClassAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return classes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(

        LayoutInflater.from(parent.context).inflate(R.layout.item_classes, parent, false)

    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(classes[position],context,week_viewed)

    }

    fun setItems(newClasses: ArrayList<Classes>) {
        classes = newClasses
        notifyDataSetChanged()
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val clas = itemView.tvclasses
        private val room = itemView.tvroom
        private val day = itemView.tvdayofweek
        private val time = itemView.tvtime



        fun bind(userClass: Classes, context: Context, week_viewed: String) {

            itemView.setOnClickListener {
                val p = layoutPosition
                ClassesDialogFragment.display((context as AppCompatActivity).supportFragmentManager,userClass,week_viewed)
            }
            clas.text = userClass.name
            room.text = userClass.room
            day.text = userClass.day
            time.text = userClass.time
        }
    }
}