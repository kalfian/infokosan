package com.kalfian.infokosan.models.properties

import com.google.gson.annotations.SerializedName

data class PropertyResponse (
    @SerializedName("success")
    val success: String,

    @SerializedName("data")
    val data: DataProperties,
)

data class DataProperties (
    @SerializedName("data")
    val data : ArrayList<Property>,

    @SerializedName("meta")
    val meta : Meta
)

data class Meta (
    @SerializedName("current_page")
    val current_page : Int,
    @SerializedName("from")
    val from : Int,
    @SerializedName("last_page")
    val last_page : Int,
    @SerializedName("path")
    val path : String,
    @SerializedName("per_page")
    val per_page : Int,
    @SerializedName("to")
    val to : Int,
    @SerializedName("total")
    val total : Int
)

data class Property (
    @SerializedName("id")
    var id : Int,

    @SerializedName("title")
    var title : String,

    @SerializedName("desc")
    var desc : String,

    @SerializedName("location")
    var location : Location,

    @SerializedName("facilities")
    var facilities : String,

    @SerializedName("room_total")
    var roomTotal : Int,

    @SerializedName("is_featured")
    var isFeatured : Int,

    @SerializedName("is_discount")
    var isDiscount : Int,

    @SerializedName("type")
    var type : Int,

    @SerializedName("type_string")
    var typeString : String,

    @SerializedName("active_status")
    var activeStatus : Int,

    @SerializedName("basic_price")
    var basicPrice : Int,

    @SerializedName("property_images")
    var propertyImages : List<PropertyImages>,

    @SerializedName("owner")
    var owner : Owner

)

data class Owner (

    @SerializedName("name")
    var name : String

)

data class PropertyImages (

    @SerializedName("id")
    var id : Int,

    @SerializedName("image")
    var image : String,

    @SerializedName("type")
    var type : String

)

data class Location (
    @SerializedName("district_id")
    var districtId : Int,
    @SerializedName("city_id")
    var cityId : Int,
    @SerializedName("postal_code")
    var postalCode : String,
    @SerializedName("address")
    var address : String,
    @SerializedName("lat")
    var lat : Double,
    @SerializedName("long")
    var long : Double,
    @SerializedName("location_string")
    var locationString : LocationString

)

data class LocationString (
    @SerializedName("district") var district : String,
    @SerializedName("city") var city : String,
    @SerializedName("province") var province : String
)