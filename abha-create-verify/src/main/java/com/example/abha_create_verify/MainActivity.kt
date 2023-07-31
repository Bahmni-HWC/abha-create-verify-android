package com.example.abha_create_verify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.abha_create_verify.data.api.RetrofitBuilder
import com.example.abha_create_verify.databinding.ActivityMainBinding
import com.example.abha_create_verify.utils.Variables
import com.example.abha_create_verify.verify.AbhaVerifyActivity


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
        RetrofitBuilder.BASE_URL = intent.getStringExtra("hipBaseURL").toString()


        binding.createAbha.setOnClickListener {
            val intent = Intent(this, CreateAbhaActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.verifyAbha.setOnClickListener {
            val stringArray = intent.getStringArrayExtra("existingABHANumbers")
            if(stringArray != null) {
                 Variables.EXISTING_ABHA_NUMBERS = stringArray
            }
            val intent = Intent(this, AbhaVerifyActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}