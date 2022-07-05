package com.paytm.hpclpos.ui.SettlementScreen.SettlementAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.livedatamodels.TransSumAvg.TransSumAvg

class SettlementAdapter(val context: Context,val arrayList: ArrayList<TransSumAvg>)
    : RecyclerView.Adapter<SettlementAdapter.Settlement>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Settlement {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_settlement_adapter, parent, false)
        return Settlement(view)
    }

    override fun onBindViewHolder(holder: Settlement, position: Int) {
        holder.transType.setText(arrayList.get(position).transType)
        holder.count.setText(arrayList.get(position).count.toString())
        holder.amount.setText(arrayList.get(position).total.toString().toString())
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class Settlement(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var transType: TextView
        var count: TextView
        var amount: TextView

        init {
            transType = itemView.findViewById(R.id.transType)
            count = itemView.findViewById(R.id.count)
            amount = itemView.findViewById(R.id.amount)
        }
    }
}