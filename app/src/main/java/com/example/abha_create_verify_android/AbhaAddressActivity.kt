package com.example.abha_create_verify_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.data.model.CreateAbhaAddressReq
import com.example.abha_create_verify_android.databinding.ActivityAbhaAddressBinding
import com.example.abha_create_verify_android.utils.Status

class AbhaAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAbhaAddressBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abha_address)
        binding = ActivityAbhaAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)

        val abhaNumberVal = intent.getStringExtra("healthIdNumber")
        binding.abhaMessage.text = "Your ABHA number : $abhaNumberVal"


        binding.proceedButton.setOnClickListener {
            viewModel.createAbhaAddress(CreateAbhaAddressReq(binding.editTextAbhaAddress.text.toString(),binding.checkbox.isChecked.toString())).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar1.visibility = View.GONE
                            resource.data?.let { data ->
                                if(data == "true") {
                                    PatientSubject().setABHAAddress(binding.editTextAbhaAddress.text.toString())
                                    binding.abhaMessage.text = "Abha Address created successfully!"
                                }
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar1.visibility= View.GONE
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            binding.progressBar1.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }

        binding.createDefaultAbhaAddressButton.setOnClickListener {
            viewModel.createDefaultAbhaAddress().observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar2.visibility = View.GONE
                            resource.data?.let { data ->
                                PatientSubject().setABHAAddress(data)
                                binding.abhaMessage.text = "Abha Address created successfully!  Your ABHA Address : "
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar2.visibility= View.GONE
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            binding.progressBar2.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

}