package com.binar.latihan_networking.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostRegisterResponse(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
): Parcelable