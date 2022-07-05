package com.paytm.hpclpos.enums

enum class ProductDetails(val productId: Int, val productName: String) {
    HSD(1, "HSD"),
    TURBOJET(51, "TURBOJET"),
    MS(52, "MS"),
    POWER(53, "POWER"),
    LUBE(54, "LUBE"),
    AUTO_LPG(55, "AUTO LPG"),
    CNG(56, "CNG");

    companion object {
        fun getProductIdByName(code: String?): Int? {
            if (code == null) {
                return null
            }
            for (e in values()) {
                if (e.productName.equals(code) || e.productName.contains(code)) {
                    return e.productId
                }
            }
            return null
        }

        fun getProductNameById(code: Int?): String? {
            if (code == null) {
                return null
            }
            for (e in values()) {
                if (e.productId.equals(code)) {
                    return e.productName
                }
            }
            return null
        }
    }
}