package com.example.abha_create_verify_android.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.example.abha_create_verify_android.MainViewModel
import com.example.abha_create_verify_android.PatientSubject
import com.facebook.react.ReactInstanceManager
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule


object ApiUtils {
    fun handlePatientDemographicsResponse(
        context: Context,
        viewModel: MainViewModel,
        reactInstanceManager: ReactInstanceManager,
        map: WritableMap,
        lifecycleOwner: LifecycleOwner
    ) {
        PatientSubject().setPatientDemographics()

        viewModel.addPatientDemographics(PatientSubject.demographics).observe(lifecycleOwner) { resource ->
            resource?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        try {
                            reactInstanceManager.currentReactContext
                                ?.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                                ?.emit("abha_response", map)
                        } catch (e: Exception) {
                            Log.e("ReactNative", "Caught Exception: ${e.message}")
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
