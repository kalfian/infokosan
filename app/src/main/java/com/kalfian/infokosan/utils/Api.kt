package com.kalfian.infokosan.utils

import com.kalfian.infokosan.models.auth.AuthResponse
import com.kalfian.infokosan.models.detail_property.DetailPropertyResponse
import com.kalfian.infokosan.models.properties.PropertyResponse
import com.kalfian.infokosan.models.register.RegisterResponse
import com.kalfian.infokosan.models.rent.RentRequest
import com.kalfian.infokosan.models.rent.RentResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @Headers("accept: application/json")
    @GET("property")
    fun getProperties(
        @QueryMap parameters: HashMap<String, String>
    ): Call<PropertyResponse>

    @Headers("accept: application/json")
    @GET("property/{Id}")
    fun getPropertyById(
        @Path("Id") propertyId: Int,
    ): Call<DetailPropertyResponse>

    @Headers("accept: application/json")
    @GET("rent/get-rent-user")
    fun getRents(
        @Header("Authorization") token: String,
    ): Call<RentResponse>

    @Headers("accept: application/json")
    @POST("rent/store-rent/{Id}")
    fun addRent(
        @Header("Authorization") token: String,
        @Body parameters: HashMap<String, Any>,
        @Path("Id") propertyId: Int,
    ): Call<RentRequest>

    @Headers("accept: application/json")
    @POST("auth/post-auth")
    fun postAuth(
            @QueryMap parameters: HashMap<String, String>
    ): Call<AuthResponse>

    @Headers("accept: application/json")
    @POST("register/post-register")
    fun postRegister(
        @QueryMap parameters: HashMap<String, String>
    ): Call<RegisterResponse>
}