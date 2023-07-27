package com.example.abha_create_verify_android

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.data.model.GenerateAadhaarOTPReq
import com.example.abha_create_verify_android.databinding.ActivityAuthModeBinding
import com.example.abha_create_verify_android.utils.Status


class AuthModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthModeBinding
    private lateinit var viewModel: MainViewModel

    private var xPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.proceedButton.setOnClickListener {
            if (xPosition == 1) {
                moveToDemographic()
            } else if (xPosition == 2) {
                moveToAadhaarOTP()
            }
        }

        val items = resources.getStringArray(R.array.menu_items)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMode.adapter = adapter

        binding.spinnerMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    applicationContext,
                    "You selected: $selectedItem",
                    Toast.LENGTH_SHORT
                ).show()

                xPosition = position


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case where no item is selected
            }
        }


    }

    private fun moveToDemographic() {
        val intent = Intent(this, DemographicActivity::class.java)
        startActivity(intent)
    }

    private fun moveToAadhaarOTP() {
        var mobileNumber : String
        val aadhaarNumber = intent.getStringExtra("aadhaarNumber")
        viewModel.generateAadhaarOtp(GenerateAadhaarOTPReq(aadhaarNumber.toString())).observe(this
        ) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { resp ->
                            mobileNumber = resp.mobileNumber
                            val intent = Intent(this, AadhaarOTPActivity::class.java)
                            intent.putExtra("mobileNumber", mobileNumber)
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
        val intent = Intent(this, CreateAbhaActivity::class.java)
        startActivity(intent)
        finish()
    }
}