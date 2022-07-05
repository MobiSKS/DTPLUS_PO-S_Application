package com.paytm.hpclpos.ui.displayparameters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.R

class DisplayParameterAdapter() : RecyclerView.Adapter<DisplayParameterAdapter.ViewHolder>() {
    lateinit var mapData: HashMap<String,String>
    lateinit var context: Activity

    // RecyclerView recyclerView;
    constructor(listdata: HashMap<String,String>, context: Activity) : this() {
        this.mapData = listdata
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.display_parameter_name_value, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView1.setText(mapData.keys.elementAt(position))
        holder.textView2.setText(mapData.values.elementAt(position))
    }

    override fun getItemCount(): Int {
        return mapData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView1: TextView
        var textView2: TextView

        init {
            textView1 = itemView.findViewById(R.id.name_value_name)
            textView2 = itemView.findViewById(R.id.name_value_val)
        }
    }
}