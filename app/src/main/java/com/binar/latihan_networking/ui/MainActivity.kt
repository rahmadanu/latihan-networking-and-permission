package com.binar.latihan_networking.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.binar.latihan_networking.presenter.MainPresenterImpl
import com.binar.latihan_networking.ui.adapter.MainAdapter
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), MainView {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainPresenterImpl: MainPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainPresenterImpl = MainPresenterImpl(this)
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
        mainPresenterImpl.fetchById()
    }

    override fun showCarById(body: GetCarByIdResponse?) {
        binding.pbLoading.isVisible = false
        Log.d("body", body?.name.toString())
        binding.tvItemResponse.text = getString(R.string.car_by_id, body?.id, body?.name, body?.price)
        binding.btnSeeImage.setOnClickListener {
            Glide.with(this)
                .load(body?.image)
                .circleCrop()
                .into(binding.ivCar)
        }
    }

    private fun fetchAllData() {
        mainPresenterImpl.fetchAllData()
    }

    override fun showList(data: List<GetAllCarResponseItem>?) {
        binding.pbLoading.isVisible = false
        val adapter = MainAdapter(object : MainAdapter.OnClickListener {
            override fun onClickItem(data: GetAllCarResponseItem) {
                Toast.makeText(this@MainActivity, "Item clicked!", Toast.LENGTH_SHORT).show()
            }
        })
        adapter.submitData(data)
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager = LinearLayoutManager(this)
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}