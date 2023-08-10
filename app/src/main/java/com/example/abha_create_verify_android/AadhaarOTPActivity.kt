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
import com.example.abha_create_verify_android.data.model.VerifyOTPReq
import com.example.abha_create_verify_android.databinding.ActivityAadhaarOtpactivityBinding
import com.example.abha_create_verify_android.utils.DialogUtils
import com.example.abha_create_verify_android.utils.Status

class AadhaarOTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAadhaarOtpactivityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAadhaarOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.appBarLayout.includeToolbar.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.proceedButton.setOnClickListener {
            binding.incorrectOTPText.visibility = View.GONE
            viewModel.verifyAadhaarOtp(VerifyOTPReq(binding.aadhaarOTPEditText.text.toString())).observe(this
            ) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            binding.correctOTPText.visibility = View.VISIBLE
                            resource.data?.let { data ->
                                PatientSubject().setAadhaarProfile(data)
                                var intent = Intent(this, AbhaMobileActivity::class.java)
                                if(data.abhaNumber != null)
                                {
                                    intent = if(data.abhaAddress != null) {
                                        Intent(this, AbhaPatientProfileActivity::class.java)
                                    } else{
                                        Intent(this, PatientBioActivity::class.java)
                                    }
                                }
                                startActivity(intent)
                                finish()
                            }
                        }

                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            binding.incorrectOTPText.visibility = View.VISIBLE
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }

                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        binding.appBarLayout.includeToolbar.customCloseButton.setOnClickListener { v ->
            DialogUtils.showConfirmationDialog(this) {
                onBackPressedDispatcher.onBackPressed()
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
        val intent = Intent(this, AuthModeActivity::class.java)
        startActivity(intent)
        finish()
    }
}