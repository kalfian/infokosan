package com.kalfian.infokosan.utils

import com.kalfian.infokosan.models.properties.PropertyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {
    @GET("property")
    fun getProperties(
        @QueryMap parameters: HashMap<String, String>
    ): Call<PropertyResponse>
}