package com.paytm.hpclpos.ui.terminalsettingfragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.Dialog.DialogUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.constants.TransactionUtils

class TerminalSettingAdapter() : RecyclerView.Adapter<TerminalSettingAdapter.ViewHolder>() {
    lateinit var mapData: HashMap<String,String>
    lateinit var context: Activity
    lateinit var terminalSettingDialog: Dialog

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
        holder.textView2.setOnClickListener({
            context.runOnUiThread({
                compareAndDisplayDialog(mapData.keys.elementAt(position),mapData.values.elementAt(position),position)
            })
        })
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

    fun callDialog(title: String, value: String,position: Int) {
        terminalSettingDialog = DialogUtil.showTerminalSettingDialog(title,value,context,object : DialogUtil.OnClickListener{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onConfirm() {
                val editTextData = DialogUtil.getTerminalSettingDialogEditText()
                when(title) {
                    context.getString(R.string.server_ip) -> {
                        GlobalMethods.setServerIp(editTextData, context)
                        mapData.put(title, editTextData)
                        notifyItemChanged(position)
                    }
                    context.getString(R.string.server_port) -> {
                        //
                    }
                    context.getString(R.string.second_server_ip) -> {
                        GlobalMethods.setSecondServerIp(editTextData, context)
                        mapData.put(title, editTextData)
                        notifyItemChanged(position)
                    }
                    context.getString(R.string.second_server_port) -> {
                        //
                    }
                    context.getString(R.string.batch_id) -> {
                        TransactionUtils.setBatchNumber(context,Constants.BATCH,editTextData)
                        mapData.put(title, editTextData)
                        notifyItemChanged(position)
                    }
                    context.getString(R.string.transaction_id) -> {
                        GlobalMethods.decrementTransactionIdByOne(context,editTextData)
                        mapData.put(title, editTextData)
                        notifyItemChanged(position)
                    }
                }
            }

            override fun onCancel() {
                terminalSettingDialog.dismiss()
            }
        })
    }

    fun compareAndDisplayDialog(title: String, value: String,position: Int) {
        when(title) {
             "TERMINAL ID" -> {
                context.runOnUiThread {
                    ToastMessages.customMsgToast(context, "Not Allowed to change TID")
                }
            }
            context.getString(R.string.server_ip),
            context.getString(R.string.server_port),
            context.getString(R.string.second_server_ip),
            context.getString(R.string.second_server_port),
            context.getString(R.string.transaction_id),
            context.getString(R.string.batch_id) -> { callDialog(title,value,position) }
        }
    }
}