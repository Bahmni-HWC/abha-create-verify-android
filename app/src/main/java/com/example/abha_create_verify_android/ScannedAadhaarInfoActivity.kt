package com.example.abha_create_verify_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.abha_create_verify_android.databinding.ActivityPatientBioBinding
import com.example.abha_create_verify_android.utils.DialogUtils
import com.example.abha_create_verify_android.utils.Variables
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.gson.Gson

class ScannedAadhaarInfoActivity : ReactActivity() {

    private lateinit var binding: ActivityPatientBioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.includeToolbar.toolbarAbha)
        
        supportActionBar?.title = resources.getString(R.string.aadhaar_info)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val patientSubject = PatientSubject.patientSubject
        binding.patientName.text = patientSubject.name
        binding.dateOfBirth.text = patientSubject.dateOfBirth
        binding.gender.text = patientSubject.gender
        binding.address.text = patientSubject.address
        binding.textPhoneNumber.visibility = View.GONE

        binding.proceedButton.text = resources.getString(R.string.finish)

        binding.proceedButton.setOnClickListener {
            exitApplication()
        }

        binding.appBarLayout.includeToolbar.customCloseButton.setOnClickListener {
            DialogUtils.showConfirmationDialog(this) {
                onBackPressedDispatcher.onBackPressed()
            }
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
            Log.e("ReactNative", "Caught Exception: ${e.message}")
        }

        onBackPressedDispatcher.onBackPressed()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleBack() {
        if(Variables.isCreateAbhaScan) {
            val intent = Intent(this, DemographicsManualOrQRScanActivity::class.java)
            startActivity(intent)
            finish()
        }
        finish()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
            .setMessage("Are you sure you want to go back to the home screen?")
            .setPositiveButton("Yes") { _, _ ->
               handleBack()
            }
            .setNegativeButton("No", null)
            .show()
    }
}