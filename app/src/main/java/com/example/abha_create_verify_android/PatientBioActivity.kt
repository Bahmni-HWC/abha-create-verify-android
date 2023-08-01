package com.example.abha_create_verify_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.databinding.ActivityPatientBioBinding
import com.example.abha_create_verify_android.utils.Status
import com.example.abha_create_verify_android.utils.Variables

class PatientBioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBioBinding
    private lateinit var viewModel: MainViewModel
    private var isABHANumberExisting = false
    private var isABHANumberLinkedToPatient = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val patientSubject = PatientSubject.patientSubject
        binding.patientName.text = patientSubject.firstName
        binding.dateOfBirth.text = patientSubject.dateOfBirth
        binding.gender.text = patientSubject.gender
        binding.phoneNumber.text = patientSubject.phoneNumber
        binding.address.text = patientSubject.address

        isABHANumberExisting = patientSubject.abhaNumber != null
        if(isABHANumberExisting) {
            binding.textAbhaNumber.visibility = View.VISIBLE
            binding.abhaNumber.visibility = View.VISIBLE
            binding.abhaNumber.text = patientSubject.abhaNumber
        }
        binding.proceedButton.setOnClickListener {
            if(isABHANumberExisting) {
                Variables.isNewABHACreated = false
                isABHANumberLinkedToPatient =  Variables.EXISTING_ABHA_NUMBERS?.contains(patientSubject.abhaNumber) == true
                if(isABHANumberLinkedToPatient)
                {
                    binding.txtLinked.visibility = View.VISIBLE
                }
                else {
                    val intent = Intent(this, AbhaAddressActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            else {
                viewModel.createHealthIdByAadhaarOtp().observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                binding.progressBar.visibility = View.GONE
                                resource.data?.let { data ->
                                    val intent = Intent(this, AbhaAddressActivity::class.java)
                                    PatientSubject().setABHANumber(data.healthIdNumber)
                                    intent.putExtra("healthIdNumber", data.healthIdNumber)
                                    Variables.isNewABHACreated = true
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
        if(isABHANumberLinkedToPatient){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to go back to the home screen?")
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(this, CreateAbhaActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("No", null)
                .show()
        }
        else {
            val intent = Intent(this, if(isABHANumberExisting) AuthModeActivity::class.java else AbhaMobileActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}