package com.binar.latihan_networking.network

import com.binar.latihan_networking.model.GetAllCarResponseItem
import com.binar.latihan_networking.model.GetCarByIdResponse
import com.binar.latihan_networking.model.PostRegisterResponse
import com.binar.latihan_networking.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("admin/car")
    fun getAllCar(): Call<List<GetAllCarResponseItem>>

    @POST("admin/auth/register")
    fun postRegister(@Body request: RegisterRequest): Call<PostRegisterResponse>

    @GET("admin/car/{id}")
    fun getCarById(@Path("id") carId: Int): Call<GetCarByIdResponse>
}