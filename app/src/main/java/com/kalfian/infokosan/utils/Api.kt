package com.kalfian.infokosan.utils

import com.kalfian.infokosan.models.detail_property.DetailPropertyResponse
import com.kalfian.infokosan.models.properties.PropertyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface Api {
    @GET("property")
    fun getProperties(
        @QueryMap parameters: HashMap<String, String>
    ): Call<PropertyResponse>

    @GET("property/{Id}")
    fun getPropertyById(
        @Path("Id") propertyId: Int,
    ): Call<DetailPropertyResponse>
}