package com.paytm.hpclpos.posterminal.printer
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.printreceipts.printmodels.KeyValueItem
import com.paytm.hpclpos.printreceipts.printmodels.NameItemView
import com.paytm.hpclpos.printreceipts.printmodels.PrintStatusListener
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.enums.SaleTransactionDetails
import java.util.*

class PrintUtils {
    companion object {
        lateinit var bitmap: Bitmap
        var gap : String? = "------------------------------------------------"

        fun shotScrollView(scrollView: ScrollView): Bitmap? {
            val childAt = scrollView.getChildAt(0)
            val bitmap = Bitmap.createBitmap(childAt.width, childAt.measuredHeight, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.WHITE)
            val canvas = Canvas(bitmap)
            scrollView.draw(canvas)
            return bitmap
        }

        fun printTRansactionCopy(bitmap: Bitmap?, listener: PrintStatusListener?) {
            val printer: PrinterTester = PrinterTester.getInstance()
            printer.init()
            printer.printBitmap(bitmap)
            printer.startPrint(listener)
        }

        fun getHeaderData(context: Context, receiptFieldList: ArrayList<ReceiptDataFieldEntity>, view: View) {
            val appRepository = AppRepository(context)
            val merchantDetails = appRepository.getMerchantDetails()
            view.findViewById<ImageView>(R.id.hpImage).setImageDrawable(context.getDrawable(R.drawable.hplogoprint))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity(context.getString(R.string.customer_copy),true,true,16)
            )
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(merchantDetails.Header1,true,true,16))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(merchantDetails.Header2,true,true,16))
        }

        fun getHeaderDataMerchantCopy(context: Context, receiptFieldList: ArrayList<ReceiptDataFieldEntity>, view: View) {
            val appRepository = AppRepository(context)
            val merchantDetails = appRepository.getMerchantDetails()
            view.findViewById<ImageView>(R.id.hpImage).setImageDrawable(context.getDrawable(R.drawable.hplogoprint))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity(context.getString(R.string.merchant_copy),true,true,16)
            )
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(merchantDetails.Header1,true,true,16))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(merchantDetails.Header2,true,true,16))
        }

        fun getSettlementHeader(context: Context, receiptFieldList: ArrayList<ReceiptDataFieldEntity>, batchNum: Int) {
            val appRepository = AppRepository(context)
            val merchantDetails = appRepository.getMerchantDetails()
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(merchantDetails.Header1,true,true,16))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(merchantDetails.Header2,true,true,16))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BATCH NO",batchNum.toString()))
            val date = DateUtils.getCurrentDateTimeForSettlement()
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(date.substring(0,7),date.substring(8)))
        }

        fun getFooterData(context: Context,receiptFieldList: ArrayList<ReceiptDataFieldEntity>) {
            val appRepository = AppRepository(context)
            val merchantDetails = appRepository.getMerchantDetails()
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(gap,true,true,16))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(merchantDetails.Footer1,true,true,16))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(merchantDetails.Footer2,true,true,16))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(gap,true,true,16))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(gap,true,true,16))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SOFTWARE VER: V1.02",true,true,16))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(gap,true,true,16))
        }

        protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
            receiptFieldList.add(receiptDataFieldEntity)
        }

        fun callPrinter(printViewContainer: View, activity: Activity, receiptFieldList: ArrayList<ReceiptDataFieldEntity>, view: View, printStatusListener: PrintStatusListener?) {

            val layout: LinearLayout = printViewContainer.findViewById(R.id.displayReceipt)
            activity.runOnUiThread({
                for (dataFieldEntity in receiptFieldList) {
                    if (dataFieldEntity.header != null) {
                        val itemView: NameItemView = dataFieldEntity.createView(layout)
                        itemView.render(dataFieldEntity)
                        layout.addView(itemView.rootView)
                    } else if (dataFieldEntity.key != null) {
                        val itemView: KeyValueItem = dataFieldEntity.createBoldView(layout)
                        itemView.render(dataFieldEntity)
                        layout.addView(itemView.rootView)
                    }
                }

                view.measure(
                    View.MeasureSpec.makeMeasureSpec(printViewContainer.layoutParams.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight())
                printViewContainer.requestLayout()
                printViewContainer.invalidate()
                bitmap = shotScrollView(printViewContainer as ScrollView)!!
            })
            Thread {
                printTRansactionCopy(bitmap, object : PrintStatusListener {
                    override fun onSuccess() {
                        activity.runOnUiThread({
                            Log.d("PrintUtils","Transaction Success")
                            GlobalMethods.initAndClear()
                            ToastMessages.customMsgToast(activity, "Receipt Printed SuccessFully")
                            printStatusListener?.onSuccess()
                        })
                    }

                    override fun onError(error: Int) {
                        ToastMessages.customMsgToast(activity, "Print Failed $error")
                        printStatusListener?.onError(error)
                    }
                })
            }.start()
        }
    }

    fun displayTransNameForPrintReciepts(saleType: String) : String {
        when(saleType) {
            SaleTransactionDetails.CCMSSALE.saleName -> { return "SALE(CCMS)" }
            SaleTransactionDetails.CARDSALE.saleName -> { return "SALE(CARD)" }
            SaleTransactionDetails.DEALER_CREDIT_SALE.saleName -> { return "SALE(CREDIT TXN)" }
            SaleTransactionDetails.CREDIT_TXN.saleName -> { return "SALE(DEALER CREDIT)" }
            SaleTransactionDetails.FASTAG_SALE_ONLY_CARDLESS_MOBILE.saleName -> { return "SALE(FASTTAG)" }
            SaleTransactionDetails.CASH_RELOAD.saleName -> {  return SaleTransactionDetails.CASH_RELOAD.saleName }
            SaleTransactionDetails.CHEQUE_RELOAD.saleName -> { return SaleTransactionDetails.CHEQUE_RELOAD.saleName }
            SaleTransactionDetails.CCMS_RELOAD.saleName -> { return SaleTransactionDetails.CCMS_RELOAD.saleName  }
            SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleName -> { return "CCMS RECHARGE(CHEQUE)" }
            SaleTransactionDetails.CCMS_NEFTRECHARGE.saleName -> { return "CCMS RECHARGE(NEFT/RTGS)" }
             SaleTransactionDetails.CCMS_CASHRECHARGE.saleName -> { return "CCMS RECHARGE(CASH)" }
            SaleTransactionDetails.CARD_FEE_PAYMENT.saleName -> { return "CARD FEE(DTP)" }
            SaleTransactionDetails.BALANCE_ENQUIRY.saleName -> { return "CARD BALANCE" }
        }
        return " "
    }
}