package com.example.abha_create_verify_android.verify

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.MainActivity
import com.example.abha_create_verify_android.MainViewModel
import com.example.abha_create_verify_android.R
import com.example.abha_create_verify_android.ViewModelFactory
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.data.model.GenerateMobileOTPReq
import com.example.abha_create_verify_android.databinding.ActivityMobileVerifyBinding
import com.example.abha_create_verify_android.utils.DialogUtils
import com.example.abha_create_verify_android.utils.Status

class VerifyMobileNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMobileVerifyBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.appBarLayout.includeToolbar.toolbarAbha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.verify_abha)


        binding.proceedButton.setOnClickListener {
            val mobileNumber = binding.mobileEditText.text.toString()
            if(mobileNumber.isEmpty() || mobileNumber.length != 10) {
                binding.errorMsg.text = String.format("Mobile number should have 10 digit")
            }
            else {
                viewModel.generateMobileOtp(GenerateMobileOTPReq(mobileNumber)).observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                binding.progressBar.visibility = View.GONE
                                resource.data?.let {
                                    val intent = Intent(this, VerifyMobileOTPActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}
