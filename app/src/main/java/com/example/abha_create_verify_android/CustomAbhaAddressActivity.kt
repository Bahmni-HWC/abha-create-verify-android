package com.example.abha_create_verify_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.data.model.CreateAbhaAddressReq
import com.example.abha_create_verify_android.databinding.ActivityCustomAbhaAddressBinding
import com.example.abha_create_verify_android.utils.Status

class CustomAbhaAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomAbhaAddressBinding
    private lateinit var viewModel: MainViewModel
    private var isVerify = false
    private var isNewABHA = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_abha_address)
        binding = ActivityCustomAbhaAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        isVerify = intent.getBooleanExtra("isVerify", false)

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(if(isVerify) R.string.verify_abha else R.string.create_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        isNewABHA = intent.getBooleanExtra("newABHA", false)
        binding.proceedButton.setOnClickListener {
            viewModel.createAbhaAddress(CreateAbhaAddressReq(binding.editTextAbhaAddress.text.toString(),binding.checkbox.isChecked.toString())).observe(this
            ) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            PatientSubject().setABHAAddress(binding.editTextAbhaAddress.text.toString() + getString(R.string.abha_suffix))
                            val intent = Intent(this, if(isVerify) AbhaPatientProfileActivity::class.java
                            else AbhaAddressSuccessActivity::class.java)
                            intent.putExtra("isVerify", isVerify)
                            startActivity(intent)
                            finish()
                        }

                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }

                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[MainViewModel::class.java]
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
           onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(this, AbhaAddressActivity::class.java)
        intent.putExtra("isVerify", isVerify)
        intent.putExtra("newABHA", isNewABHA)
        startActivity(intent)
        finish()
    }

}