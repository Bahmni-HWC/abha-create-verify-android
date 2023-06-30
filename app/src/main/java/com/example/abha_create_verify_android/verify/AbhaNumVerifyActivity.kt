package com.example.abha_create_verify_android.verify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abha_create_verify_android.AuthModeActivity
import com.example.abha_create_verify_android.R
import com.example.abha_create_verify_android.databinding.ActivityAbhaNumVerifyBinding
import com.example.abha_create_verify_android.databinding.ActivityCreateAbhaBinding

class AbhaNumVerifyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAbhaNumVerifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaNumVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.verify_abha)

        binding.proceedButton.setOnClickListener {

        }

        binding.txtLinkItNow.setOnClickListener {
            val intent = Intent(this, VerifyAuthModeActivity::class.java)
            startActivity(intent)
        }
    }
}