package com.example.abha_create_verify_android.verify

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.abha_create_verify_android.R
import com.example.abha_create_verify_android.databinding.ActivityVerifyAuthModeBinding

class VerifyAuthModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyAuthModeBinding

    private var xPosition: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyAuthModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.verify_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.proceedButton.setOnClickListener {
            if (xPosition == 1) {
                moveToMobile()
            } else if (xPosition == 2) {
                moveToAadhaarOTP()
            }
        }

        val items = resources.getStringArray(R.array.menu_items_verify)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMode.adapter = adapter

        binding.spinnerMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    applicationContext,
                    "You selected: $selectedItem",
                    Toast.LENGTH_SHORT
                ).show()

                xPosition = position


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case where no item is selected
            }
        }


    }

    private fun moveToMobile() {
//        val intent = Intent(this, ::class.java)
//        startActivity(intent)
    }

    private fun moveToAadhaarOTP() {
        val intent = Intent(this, VerifyAadhaarOTPActivity::class.java)
        startActivity(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Handle back button click here
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}