package com.binar.latihan_networking.presenter

import androidx.core.view.isVisible
import com.binar.latihan_networking.model.GetAllCarResponseItem
import com.binar.latihan_networking.model.GetCarByIdResponse
import com.binar.latihan_networking.network.ApiClient
import com.binar.latihan_networking.ui.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenterImpl(private val view: MainView): MainPresenter {
    override fun fetchById() {
        ApiClient.instance.getCarById(127)
            .enqueue(object : Callback<GetCarByIdResponse> {
                override fun onResponse(
                    call: Call<GetCarByIdResponse>,
                    response: Response<GetCarByIdResponse>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        view.showCarById(body)
                    }
                }

                override fun onFailure(call: Call<GetCarByIdResponse>, t: Throwable) {
                    view.showFailure("Failure while getting data: ${t.message}")
                }
            })
    }

    override fun fetchAllData() {
        ApiClient.instance.getAllCar()
            .enqueue(object : Callback<List<GetAllCarResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetAllCarResponseItem>>,
                    response: Response<List<GetAllCarResponseItem>>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        view.showList(body)
                    }
                }

                override fun onFailure(call: Call<List<GetAllCarResponseItem>>, t: Throwable) {
                    view.showFailure("Failure while getting data: ${t.message}")
                }
            })
    }

}