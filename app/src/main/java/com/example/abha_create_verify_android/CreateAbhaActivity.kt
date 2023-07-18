package com.example.abha_create_verify_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.widget.CheckBox
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.databinding.ActivityCreateAbhaBinding

class CreateAbhaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAbhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAbhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)

        val termsAndConditionsTextView = findViewById<TextView>(R.id.termsConditionsTextView)
        termsAndConditionsTextView.movementMethod = LinkMovementMethod.getInstance()

        termsAndConditionsTextView.setOnClickListener {
            showTermsAndConditionsPopup()
        }

        RetrofitBuilder.AUTH_TOKEN = intent.getStringExtra("sessionToken").toString()
        RetrofitBuilder.BASE_URL = intent.getStringExtra("hipBaseURL").toString()

        binding.proceedButton.setOnClickListener {
            val aadhaar = binding.aadhaarEditText.text.toString()
            val checkbox = findViewById<CheckBox>(R.id.checkbox)
            if(aadhaar.isEmpty() || aadhaar.length != 12) {
                binding.errorMsg.text = String.format("Aadhaar number should have 12 digit")
            }
            else if(!checkbox.isChecked) {
                binding.errorMsg.text = String.format("Checkbox needs to be checked")
            }
            else {
                val intent = Intent(this, AuthModeActivity::class.java)
                intent.putExtra("aadhaarNumber", binding.aadhaarEditText.text.toString())
                startActivity(intent)
                finish()
            }
        }

    }

    private fun showTermsAndConditionsPopup() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.popup_terms_conditions, null)
        dialogBuilder.setView(dialogView)

        val termsConditionsTextView = dialogView.findViewById<TextView>(R.id.tcTextView)
        termsConditionsTextView.movementMethod = ScrollingMovementMethod()

        dialogBuilder.setPositiveButton("Accept") { dialog, _ ->
            val checkbox = findViewById<CheckBox>(R.id.checkbox)
            checkbox.isChecked = true
            dialog.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.setView(dialogView, 0, 0, 0, 0)

        alertDialog.setOnShowListener {
            val scrollView = dialogView.findViewById<ScrollView>(R.id.scrollView)
            scrollView.fullScroll(ScrollView.FOCUS_UP)
        }

        alertDialog.show()
    }


}