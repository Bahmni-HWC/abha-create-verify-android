package com.example.abha_create_verify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.abha_create_verify.data.api.RetrofitBuilder
import com.example.abha_create_verify.databinding.ActivityMainBinding
import com.example.abha_create_verify.utils.Variables
import com.example.abha_create_verify.verify.AbhaVerifyActivity
import com.example.abha_create_verify.utils.Patient


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.abha)

        val token = intent.getStringExtra("sessionToken")
        if(token != null) {
            RetrofitBuilder.AUTH_TOKEN = token
        }
        val baseUrl = intent.getStringExtra("hipBaseURL")
        if(baseUrl != null) {
            RetrofitBuilder.BASE_URL = baseUrl
        }
        val stringArray = intent.getStringArrayExtra("existingABHANumbers")
        if(stringArray != null) {
            Variables.EXISTING_ABHA_NUMBERS = stringArray
        }
        Variables.isABHAVerification = false
        PatientSubject.patientSubject = Patient()

        binding.createAbha.setOnClickListener {
            val intent = Intent(this, CreateAbhaActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.verifyAbha.setOnClickListener {
            val intent = Intent(this, AbhaVerifyActivity::class.java)
            Variables.isABHAVerification = true
            startActivity(intent)
            finish()
        }

    }


}