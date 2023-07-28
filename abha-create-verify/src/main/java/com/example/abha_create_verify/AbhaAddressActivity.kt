package com.example.abha_create_verify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify.data.api.ApiHelper
import com.example.abha_create_verify.data.api.RetrofitBuilder
import com.example.abha_create_verify.databinding.ActivityAbhaAddressBinding
import com.example.abha_create_verify.utils.Status
import com.example.abha_create_verify.verify.AbhaVerifyActivity
import com.example.abha_create_verify.verify.PatientBioActivity

class AbhaAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAbhaAddressBinding
    private lateinit var viewModel: MainViewModel
    private var isVerify = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abha_address)
        binding = ActivityAbhaAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)

        isVerify = intent.getBooleanExtra("isVerify", false)
        if(isVerify){
            supportActionBar?.title = resources.getString(R.string.verify_abha)

            binding.abhaNumberCreated.text = resources.getString(R.string.no_abha_address)
        }
        else {
            supportActionBar?.title = resources.getString(R.string.create_abha)

            val abhaNumberVal = intent.getStringExtra("healthIdNumber")
            binding.abhaNumberCreated.text = String.format(binding.abhaNumberCreated.text.toString(),abhaNumberVal)
        }

        binding.abhaAddressPrompt.text = String.format(binding.abhaAddressPrompt.text.toString(), getString(R.string.abha_suffix))


        binding.createCustom.setOnClickListener {
            val intent = Intent(this, CustomAbhaAddressActivity::class.java)
            intent.putExtra("isVerify", isVerify)
            startActivity(intent)
            finish()
        }

        binding.createDefault.setOnClickListener {
            viewModel.createDefaultAbhaAddress().observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            resource.data?.let { data ->
                                PatientSubject().setABHAAddress(data.abhaAddress)
                                val intent = Intent(this, if(isVerify) PatientBioActivity::class.java
                                else AbhaAddressSuccessActivity::class.java)
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

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[MainViewModel::class.java]
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
            .setMessage("Are you sure you want to go back to the home screen?")
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent(this, if(isVerify) AbhaVerifyActivity::class.java
                else CreateAbhaActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("No", null)
            .show()
    }

}