package com.example.abha_create_verify_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.abha_create_verify_android.databinding.ActivityCreateAbhaBinding
import com.example.abha_create_verify_android.utils.DialogUtils

class CreateAbhaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAbhaBinding

    private var isFormatting: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAbhaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarLayout.includeToolbar.toolbarAbha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.create_abha)

        val termsAndConditionsTextView = findViewById<TextView>(R.id.termsConditionsTextView)
        termsAndConditionsTextView.movementMethod = LinkMovementMethod.getInstance()

        termsAndConditionsTextView.setOnClickListener {
            showTermsAndConditionsPopup()
        }

        binding.proceedButton.setOnClickListener {
            val aadhaar = binding.aadhaarEditText.text.toString().replace(" ", "")
            val checkbox = findViewById<CheckBox>(R.id.checkbox)
            if(aadhaar.isEmpty() || aadhaar.length != 12) {
                binding.errorMsg.text = String.format("Aadhaar number should have 12 digit")
            }
            else if(!checkbox.isChecked) {
                binding.errorMsg.text = String.format("Checkbox needs to be checked")
            }
            else {
                val intent = Intent(this, AuthModeActivity::class.java)
                intent.putExtra("aadhaarNumber", aadhaar)
                startActivity(intent)
                finish()
            }
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

        binding.appBarLayout.includeToolbar.customCloseButton.setOnClickListener { v ->
            DialogUtils.showConfirmationDialog(this) {
                super.onBackPressed()
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

    private fun formatAadhaarNumber(aadhaarNumber: String): String {
        val regexPattern = "(\\d{1,4})(\\d{1,4})(\\d{1,4})".toRegex()
        return aadhaarNumber.replace(regexPattern, "$1 $2 $3")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}