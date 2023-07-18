package com.example.abha_create_verify_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.abha_create_verify_android.databinding.ActivityAbhaAddressSuceessBinding

class AbhaAddressSuccessActivity : AppCompatActivity() {

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
        PatientSubject.patientSubject.covertToJson()

        binding.finishButton.setOnClickListener {
            exitApplication()
        }

    }

    private fun exitApplication() {
//        onBackPressedDispatcher.onBackPressed()
        finish()
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