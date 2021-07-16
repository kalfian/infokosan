package com.kalfian.infokosan.models.profile


import com.google.gson.annotations.SerializedName


data class ProfileResponse (
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : Data,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String,

)

data class Media (
    @SerializedName("id") var id : Int,
    @SerializedName("file") var file : String
)


data class Data (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("address") var address : String,
    @SerializedName("email") var email : String,
    @SerializedName("gender") var gender : Int,
    @SerializedName("phone") var phone : String,
    @SerializedName("image_id") var imageId : Int,
    @SerializedName("role") var role : Int,
    @SerializedName("identity") var identity : String,
    @SerializedName("active_status") var activeStatus : Int,
    @SerializedName("device_token") var deviceToken : String,
    @SerializedName("created_at") var createdAt : String,
    @SerializedName("updated_at") var updatedAt : String,
    @SerializedName("created_date") var createdDate : String,
    @SerializedName("media") var media : Media
)