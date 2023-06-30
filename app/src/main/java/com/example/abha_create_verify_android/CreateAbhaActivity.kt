package com.example.abha_create_verify_android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.abha_create_verify_android.databinding.ActivityCreateAbhaBinding


class CreateAbhaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAbhaBinding

    private var isFormatting: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAbhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)


        binding.proceedButton.setOnClickListener {
            val intent = Intent(this, AuthModeActivity::class.java)
            startActivity(intent)
        }
        binding.aadhaarEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isFormatting) {
                    isFormatting = true

                    // Remove all non-digit characters
                    val aadhaarNumber = s.toString().replace("\\D".toRegex(), "")

                    // Apply the desired format (xxxx xxxx xxxx)
                    val formattedAadhaarNumber = formatAadhaarNumber(aadhaarNumber)

                    // Update the EditText with the formatted text
                    binding.aadhaarEditText.setText(formattedAadhaarNumber)

                    // Move the cursor to the end of the text
                    binding.aadhaarEditText.setSelection(formattedAadhaarNumber.length)

                    isFormatting = false
                }
            }
        })
    }

    private fun formatAadhaarNumber(aadhaarNumber: String): String {
        val regexPattern = "(\\d{1,4})(\\d{1,4})(\\d{1,4})".toRegex()
        return aadhaarNumber.replace(regexPattern, "$1 $2 $3")
    }



}