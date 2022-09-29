package com.binar.latihan_networking.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.binar.latihan_networking.databinding.ActivityRegisterBinding
import com.binar.latihan_networking.model.PostRegisterResponse
import com.binar.latihan_networking.model.RegisterRequest
import com.binar.latihan_networking.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            btnRegister.setOnClickListener {
                if (!etEmail.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty()) {
                    registerAction(etEmail.text.toString(), etPassword.text.toString())
                } else {
                    Toast.makeText(this@RegisterActivity, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
            btnLocation.setOnClickListener {
                checkLocationPermission()
            }
        }
    }

    private fun registerAction(userEmail: String, userPassword: String) {
        val request = RegisterRequest(
            email = userEmail,
            password = userPassword,
            role = "admin"
        )

        ApiClient.instance.postRegister(request)
            .enqueue(object : Callback<PostRegisterResponse> {
                override fun onResponse(
                    call: Call<PostRegisterResponse>,
                    response: Response<PostRegisterResponse>
                ) {
                    val code = response.code()
                    if (code == 200) {
                        binding.pbLoading.isVisible = false
                        Toast.makeText(this@RegisterActivity, "Register Berhasil", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        binding.pbLoading.isVisible = false
                        Toast.makeText(this@RegisterActivity, "Email sudah tersedia", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PostRegisterResponse>, t: Throwable) {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(this@RegisterActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun checkLocationPermission() {
        val permissionCheck = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show()
            getLongLat()
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            requestLocationPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLongLat() {
        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        Toast.makeText(this, "Lat ${location?.latitude}, Long: ${location?.longitude}", Toast.LENGTH_LONG).show()
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 201)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            201 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Allowed", Toast.LENGTH_LONG).show()
                    getLongLat()
                } else {
                    Toast.makeText(this, "Denied", Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                Toast.makeText(this, "Wrong request", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}