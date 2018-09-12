package com.wojdor.hearthstonecards.application.update

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.support.v4.os.ResultReceiver

class UpdateResultReceiver @SuppressLint("RestrictedApi")
constructor(handler: Handler) : ResultReceiver(handler) {

    private var receiver: Receiver? = null

    fun setReceiver(receiver: Receiver?) {
        this.receiver = receiver
    }

    @SuppressLint("RestrictedApi")
    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        receiver?.onReceiveResult(resultCode, resultData)
    }

    interface Receiver {
        fun onReceiveResult(resultCode: Int, resultData: Bundle?)
    }

    companion object {
        const val RESULT_SUCCESS = 1
        const val RESULT_ERROR = 0
    }
}
