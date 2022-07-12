package com.paytm.hpclpos.constants

class HttpError(val errorCode: String,var message: String) {
    val httpStatusCode = HttpStatusCode.values().firstOrNull { statusCode -> statusCode.code == errorCode.toInt() } ?: HttpStatusCode.Unknown

    override fun toString(): String {
        if(message.equals("")) {
           message = httpStatusCode.name
        }

        return "RemoteServiceHttpError" +
                "\ncode: ${errorCode} (${httpStatusCode.name})" +
                "\nmessage: ${message}"
    }
}