package com.kalfian.infokosan.models.detail_property

import com.google.gson.annotations.SerializedName

data class DetailPropertyResponse (
    @SerializedName("code") var code : Int,
    @SerializedName("message") var message : String,
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : DataProperty

)
data class LocationString (
    @SerializedName("district") var district : String,
    @SerializedName("city") var city : String,
    @SerializedName("province") var province : String
)
data class Location (
    @SerializedName("district_id") var districtId : Int,
    @SerializedName("city_id") var cityId : Int,
    @SerializedName("postal_code") var postalCode : String,
    @SerializedName("address") var address : String,
    @SerializedName("lat") var lat : Double,
    @SerializedName("long") var long : Double,
    @SerializedName("location_string") var locationString : LocationString

)
data class PropertyImages (
    @SerializedName("id") var id : Int,
    @SerializedName("image") var image : String,
    @SerializedName("type") var type : String
)
data class Owner (
    @SerializedName("name") var name : String
)

data class DataProperty(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("desc") var desc: String,
    @SerializedName("location") var location: Location,
    @SerializedName("facilities") var facilities: String,
    @SerializedName("poi") var poi: String,
    @SerializedName("rules") var rules: String,
    @SerializedName("room_total") var roomTotal: Int,
    @SerializedName("is_featured") var isFeatured: Int,
    @SerializedName("is_discount") var isDiscount: Int,
    @SerializedName("type") var type: Int,
    @SerializedName("type_string") var typeString: String,
    @SerializedName("square_meter") var squareMeter: String,
    @SerializedName("basic_price") var basicPrice: Int,
    @SerializedName("property_images") var propertyImages: List<PropertyImages>,
    @SerializedName("owner") var owner: Owner

)