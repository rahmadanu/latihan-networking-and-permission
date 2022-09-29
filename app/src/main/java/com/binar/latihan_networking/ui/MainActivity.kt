package com.binar.latihan_networking.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.latihan_networking.R
import com.binar.latihan_networking.databinding.ActivityMainBinding
import com.binar.latihan_networking.model.GetAllCarResponseItem
import com.binar.latihan_networking.model.GetCarByIdResponse
import com.binar.latihan_networking.network.ApiClient
import com.binar.latihan_networking.ui.adapter.MainAdapter
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchAllData()
        fetchById()
        setupView()
    }

    private fun setupView() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun fetchById() {
        ApiClient.instance.getCarById(90)
            .enqueue(object : Callback<GetCarByIdResponse> {
                override fun onResponse(
                    call: Call<GetCarByIdResponse>,
                    response: Response<GetCarByIdResponse>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        showCarById(body)
                        binding.pbLoading.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                    }
                }

                override fun onFailure(call: Call<GetCarByIdResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun showCarById(body: GetCarByIdResponse?) {
        binding.tvItemResponse.text = getString(R.string.car_by_id, body?.id, body?.name, body?.price)
        binding.btnSeeImage.setOnClickListener {
            Glide.with(this)
                .load(body?.image)
                .circleCrop()
                .into(binding.ivCar)
        }
    }

    private fun fetchAllData() {
        ApiClient.instance.getAllCar()
            .enqueue(object : Callback<List<GetAllCarResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetAllCarResponseItem>>,
                    response: Response<List<GetAllCarResponseItem>>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        showList(body)
                        binding.pbLoading.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                    }
                }

                override fun onFailure(call: Call<List<GetAllCarResponseItem>>, t: Throwable) {
                    binding.pbLoading.isVisible = false
                }
            })
    }

    private fun showList(data: List<GetAllCarResponseItem>?) {
        val adapter = MainAdapter(object : MainAdapter.OnClickListener {
            override fun onClickItem(data: GetAllCarResponseItem) {
                Toast.makeText(this@MainActivity, "Item clicked!", Toast.LENGTH_SHORT).show()
            }
        })
        adapter.submitData(data)
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager = LinearLayoutManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}