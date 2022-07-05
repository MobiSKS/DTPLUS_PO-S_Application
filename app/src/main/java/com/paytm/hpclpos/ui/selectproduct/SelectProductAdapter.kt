package com.paytm.hpclpos.ui.selectproduct

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.R

class SelectProductAdapter() : RecyclerView.Adapter<SelectProductAdapter.ViewHolder>() {
    lateinit var listdata: List<String>
    lateinit var context: Activity
    lateinit var navigateListener: NavigateListener

    // RecyclerView recyclerView;
    constructor(listdata: List<String>, context: Activity, navigateListener: NavigateListener) : this() {
        this.listdata = listdata
        this.context = context
        this.navigateListener = navigateListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.setText(listdata[position])
               holder.relativeLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                navigateListener.navigate(listdata[position])
            }
        })
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var relativeLayout: RelativeLayout
        init {
            textView = itemView.findViewById(R.id.item)
            relativeLayout = itemView.findViewById(R.id.relativeLayout)
        }
    }
}