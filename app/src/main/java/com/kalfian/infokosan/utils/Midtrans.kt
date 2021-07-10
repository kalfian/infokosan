package com.kalfian.infokosan.utils

import android.content.Context
import com.kalfian.infokosan.BuildConfig
import com.kalfian.infokosan.adapters.GridPropertyAdapter
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.UIKitCustomSetting
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.uikit.SdkUIFlowBuilder

class Midtrans(ctx: Context, activity: TransactionFinishedCallback) {

    private var ctx = ctx
    private var activity = activity

    fun initSdk() {
        val clientKey = BuildConfig.MidtransClient
        val baseUrl = BuildConfig.MidtransBaseUrl
        val sdkUIFlowBuilder: SdkUIFlowBuilder = SdkUIFlowBuilder.init()
            .setClientKey(clientKey) // client_key is mandatory
            .setContext(ctx) // context is mandatory
            .setTransactionFinishedCallback(activity) // set transaction finish callback (sdk callback)
            .setMerchantBaseUrl(baseUrl) //set merchant url
            .setUIkitCustomSetting(uiKitCustomSetting())
            .enableLog(true) // enable sdk log
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")) // will replace theme on snap theme on MAP
            .setLanguage("en")
        sdkUIFlowBuilder.buildSDK()
    }

    fun snapToken(snaptokenValue: String) {
        MidtransSDK.getInstance().startPaymentUiFlow(ctx, snaptokenValue)
    }

    private fun uiKitCustomSetting(): UIKitCustomSetting {
        val uIKitCustomSetting = UIKitCustomSetting()
        uIKitCustomSetting.isSkipCustomerDetailsPages = true
        uIKitCustomSetting.isShowPaymentStatus = true
        return uIKitCustomSetting
    }
}