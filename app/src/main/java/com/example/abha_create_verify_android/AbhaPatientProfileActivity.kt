package com.example.abha_create_verify_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.databinding.ActivityPatientBioBinding
import com.example.abha_create_verify_android.utils.ApiUtils
import com.example.abha_create_verify_android.utils.Variables
import com.example.abha_create_verify_android.verify.AbhaVerifyActivity
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.google.gson.Gson

class AbhaPatientProfileActivity : ReactActivity() {

    private lateinit var binding: ActivityPatientBioBinding
    private lateinit var viewModel: MainViewModel
    private var isVerify = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)

        isVerify = intent.getBooleanExtra("isVerify", false)
        supportActionBar?.title = if(isVerify) resources.getString(R.string.verify_abha) else  resources.getString(R.string.create_abha)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val patientSubject = PatientSubject.patientSubject
        binding.patientName.text = patientSubject.firstName
        binding.dateOfBirth.text = patientSubject.dateOfBirth
        binding.gender.text = patientSubject.gender
        binding.phoneNumber.text = patientSubject.phoneNumber
        binding.address.text = patientSubject.address
        binding.abhaNumber.text = patientSubject.abhaNumber
        binding.abhaAddress.text = patientSubject.abhaAddress

        binding.textAbhaNumber.visibility = View.VISIBLE
        binding.abhaNumber.visibility = View.VISIBLE
        binding.textAbhaAddress.visibility = View.VISIBLE
        binding.abhaAddress.visibility = View.VISIBLE

        binding.proceedButton.text = resources.getString(R.string.finish)

        binding.proceedButton.setOnClickListener {
            if(Variables.EXISTING_ABHA_NUMBERS?.contains(patientSubject.abhaNumber) == true)
            {
                binding.txtLinked.visibility = View.VISIBLE
            }
            else {
                exitApplication()
            }
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