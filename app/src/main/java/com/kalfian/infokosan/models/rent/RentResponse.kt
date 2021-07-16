package com.kalfian.infokosan.models.rent

import com.google.gson.annotations.SerializedName

data class RentResponse (
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : List<DataRent>,
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String )

data class Pivot (
    @SerializedName("property_id") var propertyId : Int,
    @SerializedName("media_id") var mediaId : Int,
    @SerializedName("id") var id : Int
)

data class Gallery (

    @SerializedName("id") var id : Int,
    @SerializedName("user_id") var userId : Int,
    @SerializedName("file") var file : String,
    @SerializedName("type") var type : String,
    @SerializedName("created_at") var createdAt : String,
    @SerializedName("updated_at") var updatedAt : String,
    @SerializedName("pivot") var pivot : Pivot

)

data class Property (

    @SerializedName("id") var id : Int,
    @SerializedName("user_id") var userId : Int,
    @SerializedName("title") var title : String,
    @SerializedName("desc") var desc : String,
    @SerializedName("district_id") var districtId : Int,
    @SerializedName("postal_code") var postalCode : String,
    @SerializedName("address") var address : String,
    @SerializedName("lat") var lat : Double,
    @SerializedName("long") var long : Double,
    @SerializedName("facilities") var facilities : String,
    @SerializedName("poi") var poi : String,
    @SerializedName("rules") var rules : String,
    @SerializedName("room_total") var roomTotal : Int,
    @SerializedName("type") var type : Int,
    @SerializedName("square_meter") var squareMeter : String,
    @SerializedName("active_status") var activeStatus : Int,
    @SerializedName("is_discount") var isDiscount : Int,
    @SerializedName("is_featured") var isFeatured : Int,
    @SerializedName("basic_price") var basicPrice : Int,
    @SerializedName("created_at") var createdAt : String,
    @SerializedName("updated_at") var updatedAt : String,
    @SerializedName("gallery") var gallery : List<Gallery>

)

data class RefStatus (

    @SerializedName("id") var id : Int,
    @SerializedName("ref") var ref : Int,
    @SerializedName("title") var title : String,
    @SerializedName("created_at") var createdAt : String,
    @SerializedName("updated_at") var updatedAt : String

)

data class Payment (

    @SerializedName("id") var id : Int,
    @SerializedName("code") var code : String,
    @SerializedName("payment_response") var paymentResponse : String,
    @SerializedName("amount") var amount : String,
    @SerializedName("admin_cost") var adminCost : String,
    @SerializedName("name") var name : String,
    @SerializedName("status_payment") var statusPayment : String,
    @SerializedName("snap_token") var snapToken : String,
    @SerializedName("paid_at") var paidAt : String,
    @SerializedName("created_at") var createdAt : String,
    @SerializedName("updated_at") var updatedAt : String,
    @SerializedName("ref_status") var refStatus : RefStatus

)

data class DataRent (
    @SerializedName("id") var id : Int,
    @SerializedName("property_id") var propertyId : Int,
    @SerializedName("user_id") var userId : Int,
    @SerializedName("payment_id") var paymentId : Int,
    @SerializedName("active_status") var activeStatus : Int,
    @SerializedName("enter_date") var enterDate : String,
    @SerializedName("created_at") var createdAt : String,
    @SerializedName("updated_at") var updatedAt : String,
    @SerializedName("property") var property : List<Property>,
    @SerializedName("payment") var payment : Payment
)