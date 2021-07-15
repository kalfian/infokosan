package com.kalfian.infokosan.models.register

import com.google.gson.annotations.SerializedName


data class RegisterResponse (

    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : String,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String

)