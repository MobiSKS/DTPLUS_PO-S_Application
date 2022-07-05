package com.paytm.hpclpos.constants

import com.paytm.hpclpos.livedatamodels.registrationapi.ObjBanks
import com.paytm.hpclpos.livedatamodels.registrationapi.ObjFormFactors
import com.paytm.hpclpos.livedatamodels.registrationapi.ObjGetRegistrationProcessMerchant
import com.paytm.hpclpos.livedatamodels.registrationapi.ObjGetRegistrationProcessTrans

class RegistartionUtils {
    companion object {
        fun getObjTypeForRegistrationProcessMerchant( objGetRegistrationProcessMerchant: ObjGetRegistrationProcessMerchant)
                : com.paytm.hpclpos.roomDatabase.entity.ObjGetRegistrationProcessMerchant {
            val objRpm = com.paytm.hpclpos.roomDatabase.entity.ObjGetRegistrationProcessMerchant()
            objRpm.MerchantAddress = objGetRegistrationProcessMerchant.MerchantAddress
            objRpm.MerchantId = objGetRegistrationProcessMerchant.MerchantId
            objRpm.MerchantName = objGetRegistrationProcessMerchant.MerchantName
            objRpm.TerminalId = objGetRegistrationProcessMerchant.TerminalId!!.toLong()
            objRpm.Header1 = objGetRegistrationProcessMerchant.Header1!!
            objRpm.Header2 = objGetRegistrationProcessMerchant.Header2!!
            objRpm.Footer1 = objGetRegistrationProcessMerchant.Footer1!!
            objRpm.Footer2 = objGetRegistrationProcessMerchant.Footer2!!
            objRpm.BatchSaleLimit = objGetRegistrationProcessMerchant.BatchSaleLimit!!
            objRpm.BatchReloadLimit = objGetRegistrationProcessMerchant.BatchReloadLimit!!
            objRpm.BatchSize = objGetRegistrationProcessMerchant.BatchSize!!
            objRpm.SettlementTime = objGetRegistrationProcessMerchant.SettlementTime!!
            objRpm.RemoteDownload = objGetRegistrationProcessMerchant.RemoteDownload!!
            objRpm.BatchNo = objGetRegistrationProcessMerchant.BatchNo!!
            objRpm.URL = objGetRegistrationProcessMerchant.URL!!
            return objRpm
        }

        fun getObjTypeForRegistrationProcessTrans( objGetRegistrationProcessTrans: ObjGetRegistrationProcessTrans)
                : com.paytm.hpclpos.roomDatabase.entity.ObjGetRegistrationProcessTrans {
            val objRpm = com.paytm.hpclpos.roomDatabase.entity.ObjGetRegistrationProcessTrans()
            objRpm.TransType = objGetRegistrationProcessTrans.TransType
            objRpm.TransName = objGetRegistrationProcessTrans.TransName
            objRpm.MinVal = objGetRegistrationProcessTrans.MinVal
            objRpm.MaxVal = objGetRegistrationProcessTrans.MaxVal
            return objRpm
        }

        fun getObjBanks(objBanks: ObjBanks) : com.paytm.hpclpos.roomDatabase.entity.ObjBanks{
            val objBank = com.paytm.hpclpos.roomDatabase.entity.ObjBanks()
            objBank.Name = objBanks.Name
            objBank.Value = objBanks.Value
            return objBank
        }

        fun getObjFormFactors(objFormFactors: ObjFormFactors) : com.paytm.hpclpos.roomDatabase.entity.ObjFormFactors{
            val objFormFactor = com.paytm.hpclpos.roomDatabase.entity.ObjFormFactors()
            objFormFactor.Name = objFormFactors.Name
            objFormFactor.Value = objFormFactors.Value
            return objFormFactor
        }
    }
}