package com.example.abha_create_verify_android.verify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.MainViewModel
import com.example.abha_create_verify_android.R
import com.example.abha_create_verify_android.ViewModelFactory
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.data.model.MobileNumberLinkedABHA
import com.example.abha_create_verify_android.data.model.VerifyOTPReq
import com.example.abha_create_verify_android.databinding.ActivityVerifyOtpactivityBinding
import com.example.abha_create_verify_android.utils.DialogUtils
import com.example.abha_create_verify_android.utils.Status

class VerifyMobileOTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyOtpactivityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.appBarLayout.includeToolbar.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.verify_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.OTPTextView.text = resources.getString(R.string.enter_otp)

        binding.proceedButton.setOnClickListener {
            viewModel.verifyOtp(VerifyOTPReq(binding.otpEditText.text.toString())).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            binding.correctOTPText.visibility = View.VISIBLE
                            resource.data?.let { data ->
                                val linkedABHAs: ArrayList<MobileNumberLinkedABHA> = data as ArrayList<MobileNumberLinkedABHA>
                                val intent = Intent(this, MobileLinkedABHAActivity::class.java)
                                intent.putExtra("linkedABHAs", linkedABHAs);
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
        val intent = Intent(this, VerifyMobileNumberActivity::class.java)
        startActivity(intent)
        finish()
    }
}

