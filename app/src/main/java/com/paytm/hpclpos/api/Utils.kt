package com.paytm.hppay.api


object Utils {
    const val MAIN_URL = "http://182.74.202.219:8001/HPPayWebAPILPG/api/LPGProfileCreation/"
    const val MAIN_URL_HPPAY = "http://180.179.222.161/dtpwebapi/"
    const val UAT_URL_HPPAY ="https://dtpapi.mloyalretail.com/"
    const val GET_GENERATE_BATCH_NO = "/dtpwebapi/api/dtplus/transaction/get_batch_no"
    const val GET_GENERATE_TOKEN = "api/dtplus/generate_token"
    const val GET_SET_ALL_CONFIGURATION_FOR_TERMINAL = "/terminal/api/edc/login/set_all_configurations_for_terminal"
    const val GET_RELOAD_API_BY_CASH = "terminal/api/edc/transaction/reload_api_by_cash"
    const val GET_RELOAD_API_BY_CHEQUE = "terminal/api/edc/transaction/reload_api_by_cheque"
    const val GET_RELOAD_API_BY_NEFT = "terminal/api/edc/transaction/reload_api_by_neft_rtgs"
    const val GET_CCMS_SALE_BY_CARD = "terminal/api/edc/transaction/ccms_sale_by_card"
    const val GET_CREDIT_TXN_BY_CARD = "terminal/api/edc/transaction/credit_sale_by_card"
    const val GET_CARD_FEE = "terminal/api/edc/transaction/pay_card_fee"
    const val GET_SEND_OTP = "terminal/api/edc/login/send_otp"
    const val GET_AUTH_OTP = "terminal/api/edc/login/authenticate"
    const val GET_CCMS_SALE_BY_MO_NO = "terminal/api/edc/transaction/ccms_sale_by_mobile_no"
    const val GET_VALIDATE_PIN = "terminal/api/edc/login/validate_pin"
    const val GET_CASH_RELOAD = GET_RELOAD_API_BY_CASH
    const val GET_CHEQUE_RELOAD = GET_RELOAD_API_BY_CHEQUE
    const val GET_NEFT_RELOAD = "terminal/api/edc/transaction/reload_api_by_neft_rtgs"
    const val GET_CREDIT_TXN_BY_MO_NO = "terminal/api/edc/transaction/credit_sale_by_mobile_no"

    const val GET_CASH_RELOAD_MOBILE_NO = "terminal/api/edc/transaction/reload_api_by_cash_mobile"
    const val GET_CHEQUE_RELOAD_MOBILE_NO = "terminal/api/edc/transaction/reload_api_by_cheque_mobile"
    const val GET_NEFT_RELOAD_MOBILE_NO = "terminal/api/edc/transaction/reload_api_by_neft_rtgs_mobile"

    const val GET_CREDIT_SALE_BY_CARD = "terminal/api/edc/transaction/credit_sale_by_card"
    const val GET_CARD_SALE_BY_NO = "terminal/api/edc/transaction/card_sale_by_mobileno"
    const val GET_WALLET_BALANCE_LIMIT = "terminal/api/edc/wallet/get_wallet_balance_limit"
    const val GET_CARD_SALE_BY_CARD = "terminal/api/edc/transaction/card_sale_by_card"
    const val GET_BATCH_UPLOAD = "terminal/api/edc/terminals/batch_upload"
    const val GET_CCMS_RECHARGE_BY_CASH = GET_RELOAD_API_BY_CASH
    const val GET_CCMS_RECHARGE_BY_MONO = "terminal/api/edc/transaction/reload_api_by_cash_mobile"
    const val GET_CCMS_RECHARGE_BY_CHEQUE = GET_RELOAD_API_BY_CHEQUE

    const val GET_CCMS_RECHARGE_API = "api/dtplus/transaction/recharge_ccms_account"
    const val GET_GENERATE_OTP = "api/dtplus/transaction/generate_otp"
    const val GET_CCMS_SALE = "api/dtplus/transaction/sale_by_terminal"
    const val GET_CCMS_RECHARGESALE = "api/dtplus/transaction/recharge_ccms_account"
    const val GET_BATCH_SETTLEMENT = "api/dtplus/transaction/check_transcations_for_batch_settlement"
    const val GET_CARD_FEE_PAYMENT = "api/dtplus/transaction/card_fee_payment"
    const val GET_BALANCE_TRANSFER = "api/dtplus/transaction/card_fee_payment"
    const val GET_OTP = "api/dtplus/transaction/generate_otp"
    const val GET_REGISTRATION = "api/dtplus/transaction/get_registration_parameters"
    const val GET_RELOAD = "api/dtplus/transaction/reload_account"
    const val GET_BALANCE_ENQUIRY = "api/dtplus/transaction/balance_enquiry"
    const val GET_CCMS_BALANCE_ENQUIRY = "api/dtplus/transaction/ccms_balance_enquiry"
    const val GET_UNBLOCK_CARD_PIN = "api/dtplus/transaction/unblock_card_pin"
    const val GET_CHANGE_CARD_PIN = "api/dtplus/transaction/change_card_pin"
    const val GET_CONTROL_CHANGE_PIN = "api/dtplus/transaction/control_card_pin_change"
    const val GET_IDFC_GET_OTP = "api/dtplus/IDFC/idfc_get_otp"
}