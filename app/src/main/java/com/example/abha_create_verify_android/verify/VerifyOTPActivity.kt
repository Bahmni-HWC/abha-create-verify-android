package com.example.abha_create_verify_android.verify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.AbhaAddressActivity
import com.example.abha_create_verify_android.MainViewModel
import com.example.abha_create_verify_android.PatientSubject
import com.example.abha_create_verify_android.R
import com.example.abha_create_verify_android.ViewModelFactory
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.data.model.ConfirmOtpReq
import com.example.abha_create_verify_android.databinding.ActivityVerifyOtpactivityBinding
import com.example.abha_create_verify_android.utils.Status

class VerifyOTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyOtpactivityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.verify_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.proceedButton.setOnClickListener {
            viewModel.confirmOtp(ConfirmOtpReq(binding.otpEditText.text.toString(),intent.getStringExtra("authMode").toString().replace(" ","_"))).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            binding.correctOTPText.visibility = View.VISIBLE
                            resource.data?.let { data ->
                                PatientSubject().setPatient(data)
                                if(data.abhaAddress == null) {
                                    val intent = Intent(this, AbhaAddressActivity::class.java)
                                    intent.putExtra("isVerify", true)
                                    startActivity(intent)
                                    finish()
                                }
                                else {
                                    val intent = Intent(this, PatientBioActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
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
        val intent = Intent(this, AbhaVerifyActivity::class.java)
        startActivity(intent)
        finish()
    }
}

