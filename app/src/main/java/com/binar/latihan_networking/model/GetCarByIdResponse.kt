package com.binar.latihan_networking.model


import com.google.gson.annotations.SerializedName

data class GetCarByIdResponse(
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("finish_rent_at")
    val finishRentAt: Any? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("start_rent_at")
    val startRentAt: Any? = null,
    @SerializedName("status")
    val status: Boolean? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
)