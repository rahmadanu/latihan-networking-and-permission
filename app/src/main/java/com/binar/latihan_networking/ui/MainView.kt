package com.binar.latihan_networking.ui

import com.binar.latihan_networking.model.GetAllCarResponseItem
import com.binar.latihan_networking.model.GetCarByIdResponse

interface MainView {

    fun showCarById(body: GetCarByIdResponse?)
    fun showList(data: List<GetAllCarResponseItem>?)
    fun showFailure(message: String)
}