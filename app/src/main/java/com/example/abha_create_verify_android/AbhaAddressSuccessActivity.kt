package com.example.abha_create_verify_android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.abha_create_verify_android.databinding.ActivityAbhaAddressSuceessBinding
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.gson.Gson

class AbhaAddressSuccessActivity : ReactActivity() {

    private lateinit var binding: ActivityAbhaAddressSuceessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abha_address_suceess)
        binding = ActivityAbhaAddressSuceessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)

        binding.abhaNumber.text =  PatientSubject.patientSubject.abhaNumber
        binding.abhaAddress.text =  PatientSubject.patientSubject.abhaAddress

        binding.finishButton.setOnClickListener {
            exitApplication()
        }

    }

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
//                val intent = Intent(this, CreateAbhaActivity::class.java)
//                startActivity(intent)
                onBackPressedDispatcher.onBackPressed()
            }
            .setNegativeButton("No", null)
            .show()
    }

}