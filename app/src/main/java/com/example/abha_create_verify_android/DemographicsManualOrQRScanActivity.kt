package com.example.abha_create_verify_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.abha_create_verify_android.databinding.ActivityDemographicsManualOrQrscanBinding
import com.example.abha_create_verify_android.utils.DialogUtils
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


class DemographicsManualOrQRScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDemographicsManualOrQrscanBinding
    private val successMessage = MutableLiveData<String>()
    private val failureMessage = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemographicsManualOrQrscanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.includeToolbar.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.scanQRButton.setOnClickListener {
            launchScanner()
        }

        binding.enterManuallyButton.setOnClickListener {
            moveToManualDemographic()
        }
        binding.appBarLayout.includeToolbar.customCloseButton.setOnClickListener { v ->
            DialogUtils.showConfirmationDialog(this) {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        successMessage.observe(this, Observer { message ->
            showMessage(message)
        })

        failureMessage.observe(this, Observer { message ->
            showMessage(message)
        })
    }

    private fun launchScanner(): String? {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .build()
        var data: String? = null;

        val scanner = GmsBarcodeScanning.getClient(this, options)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                data = barcode.rawValue;
                Log.d("QR", "Successful: $data")
                successMessage.postValue("Successfully done scanning: $data")
            }
            .addOnFailureListener { e ->
                val errorMessage = "Some unexpected error occurred: $e"
                Log.d("QR", "failed:  $e")
                failureMessage.postValue(errorMessage)
            }
        return data;
    }

    private fun moveToManualDemographic() {
        val aadhaarNumber = intent.getStringExtra("aadhaarNumber")
        val intent = Intent(this, DemographicActivity::class.java)
        intent.putExtra("aadhaarNumber", aadhaarNumber)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        val intent = Intent(this, AuthModeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}