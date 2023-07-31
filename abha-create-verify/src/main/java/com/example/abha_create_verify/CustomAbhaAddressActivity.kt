package com.example.abha_create_verify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify.data.api.ApiHelper
import com.example.abha_create_verify.data.api.RetrofitBuilder
import com.example.abha_create_verify.data.model.CreateAbhaAddressReq
import com.example.abha_create_verify.databinding.ActivityCustomAbhaAddressBinding
import com.example.abha_create_verify.utils.Status
import com.example.abha_create_verify.verify.PatientBioActivity

class CustomAbhaAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomAbhaAddressBinding
    private lateinit var viewModel: MainViewModel
    private var isVerify = false


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

        binding.proceedButton.setOnClickListener {
            viewModel.createAbhaAddress(CreateAbhaAddressReq(binding.editTextAbhaAddress.text.toString(),binding.checkbox.isChecked.toString())).observe(this
            ) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            PatientSubject().setABHAAddress(binding.editTextAbhaAddress.text.toString() + getString(R.string.abha_suffix))
                            val intent = Intent(this, if(isVerify) PatientBioActivity::class.java
                            else AbhaAddressSuccessActivity::class.java)
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
        startActivity(intent)
        finish()
    }

}