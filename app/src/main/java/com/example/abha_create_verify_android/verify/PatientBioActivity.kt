package com.example.abha_create_verify_android.verify

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.abha_create_verify_android.PatientSubject
import com.example.abha_create_verify_android.R
import com.example.abha_create_verify_android.databinding.ActivityPatientBioBinding
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.gson.Gson

class PatientBioActivity : ReactActivity() {

    private lateinit var binding: ActivityPatientBioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.verify_abha)
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
             exitApplication()
        }
    }

    @SuppressLint("VisibleForTests")
    private fun exitApplication() {
        val map: WritableMap = Arguments.createMap()
        val gson = Gson()
        val jsonString = gson.toJson(PatientSubject.patientSubject)
        map.putString("patientInfo", jsonString)
        try {
            reactInstanceManager.currentReactContext
                ?.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                ?.emit("abha_response", map)
        } catch (e: Exception) {
            Log.e("ReactNative", "Caught Exception: " + e.message)
        }
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
            .setMessage("Are you sure you want to go back to the home screen?")
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent(this, AbhaVerifyActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("No", null)
            .show()
    }
}