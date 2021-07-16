package com.kalfian.infokosan.utils

import com.kalfian.infokosan.models.auth.AuthResponse
import com.kalfian.infokosan.models.detail_property.DetailPropertyResponse
import com.kalfian.infokosan.models.properties.PropertyResponse
import com.kalfian.infokosan.models.register.RegisterResponse
import com.kalfian.infokosan.models.rent.RentResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("property")
    fun getProperties(
        @QueryMap parameters: HashMap<String, String>
    ): Call<PropertyResponse>

    @GET("property/{Id}")
    fun getPropertyById(
        @Path("Id") propertyId: Int,
    ): Call<DetailPropertyResponse>

    @GET("rent/get-rent-user")
    fun getRents(
        @Header("Authorization") token: String,
    ): Call<RentResponse>

    @POST("auth/post-auth")
    fun postAuth(
            @QueryMap parameters: HashMap<String, String>
    ): Call<AuthResponse>

    @POST("register/post-register")
    fun postRegister(
        @QueryMap parameters: HashMap<String, String>
    ): Call<RegisterResponse>
}