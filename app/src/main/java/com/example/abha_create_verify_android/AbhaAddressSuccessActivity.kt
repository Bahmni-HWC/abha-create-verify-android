package com.example.abha_create_verify_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.databinding.ActivityAbhaAddressSuceessBinding
import com.example.abha_create_verify_android.utils.ApiUtils
import com.example.abha_create_verify_android.verify.AbhaVerifyActivity
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.google.gson.Gson

class AbhaAddressSuccessActivity : ReactActivity() {

    private lateinit var binding: ActivityAbhaAddressSuceessBinding
    private lateinit var viewModel: MainViewModel
    private var isVerify = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abha_address_suceess)
        binding = ActivityAbhaAddressSuceessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)

        isVerify = intent.getBooleanExtra("isVerify", false)
        supportActionBar?.title = if(isVerify) resources.getString(R.string.verify_abha) else  resources.getString(R.string.create_abha)


        binding.abhaNumber.text =  PatientSubject.patientSubject.abhaNumber
        binding.abhaAddress.text =  PatientSubject.patientSubject.abhaAddress

        binding.finishButton.setOnClickListener {
            exitApplication()
        }

    }

    @SuppressLint("VisibleForTests")
    private fun exitApplication() {
        val map: WritableMap = Arguments.createMap()
        val gson = Gson()
        val jsonString = gson.toJson(PatientSubject.patientSubject)
        map.putString("patientInfo", jsonString)

        ApiUtils.handlePatientDemographicsResponse(this,viewModel, reactInstanceManager, map, this)

        onBackPressedDispatcher.onBackPressed()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[MainViewModel::class.java]
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
            .setMessage("Are you sure you want to go back to the home screen?")
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent(this, if(isVerify) AbhaVerifyActivity::class.java
                else CreateAbhaActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("No", null)
            .show()
    }

}