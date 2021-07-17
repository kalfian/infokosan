package com.kalfian.infokosan.models.rent

import com.google.gson.annotations.SerializedName


data class RentRequest (
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : DataPostRent,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String

)

data class DataPostRent (
    @SerializedName("payment_code") var paymentCode : String,
    @SerializedName("payment_link") var paymentLink : String,
    @SerializedName("payment_id") var paymentId : String
)