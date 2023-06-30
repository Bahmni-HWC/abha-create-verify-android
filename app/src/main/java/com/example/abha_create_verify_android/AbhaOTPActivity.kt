package com.example.abha_create_verify_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.data.model.VerifyAadhaarOTPResp
import com.example.abha_create_verify_android.data.model.VerifyOTPReq
import com.example.abha_create_verify_android.databinding.ActivityAbhaOtpactivityBinding
import com.example.abha_create_verify_android.utils.Status

class AbhaOTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAbhaOtpactivityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.proceedButton.setOnClickListener {
            binding.incorrectOTPText.visibility = View.GONE
            viewModel.verifyMobileOtp(VerifyOTPReq(binding.OTPEditText.text.toString())).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            binding.correctOTPText.visibility = View.VISIBLE
                            resource.data?.let { data ->
                                PatientSubject().setMobile(intent.getStringExtra("mobileNumber")!!)
                                val intent = Intent(this, PatientBioActivity::class.java)
                                startActivity(intent)
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility= View.GONE
                            binding.incorrectOTPText.visibility = View.VISIBLE
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Handle back button click here
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}