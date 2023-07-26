package com.example.abha_create_verify_android.verify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.AbhaOTPActivity
import com.example.abha_create_verify_android.MainActivity
import com.example.abha_create_verify_android.MainViewModel
import com.example.abha_create_verify_android.PatientBioActivity
import com.example.abha_create_verify_android.PatientSubject
import com.example.abha_create_verify_android.R
import com.example.abha_create_verify_android.ViewModelFactory
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.data.model.GenerateMobileOTPReq
import com.example.abha_create_verify_android.data.model.SearchAbhaReq
import com.example.abha_create_verify_android.databinding.ActivityAbhaVerifyBinding
import com.example.abha_create_verify_android.utils.Status
import com.example.abha_create_verify_android.utils.Variables

class AbhaVerifyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAbhaVerifyBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.verify_abha)

        val termsAndConditionsTextView = findViewById<TextView>(R.id.termsConditionsTextView)
        termsAndConditionsTextView.movementMethod = LinkMovementMethod.getInstance()

        termsAndConditionsTextView.setOnClickListener {
            showTermsAndConditionsPopup()
        }


        binding.proceedButton.setOnClickListener {
            var abhaId = binding.abhaEditText.text.toString()
            val checkbox = findViewById<CheckBox>(R.id.checkbox)
            if(abhaId.isEmpty() || abhaId.length != 14) {
                binding.errorMsg.text = String.format("Abha number should have ")
            }
            else if(!checkbox.isChecked) {
                binding.errorMsg.text = String.format("Checkbox needs to be checked")
            }
            else {
                if(!checkIfAbhaNumerExists(abhaId)) {
                    viewModel.searchAbhaId(SearchAbhaReq(abhaId)).observe(this) {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    binding.progressBar.visibility = View.GONE
                                    resource.data?.let { data ->
                                        val intent = Intent(this, AuthModeActivity::class.java)
                                        intent.putStringArrayListExtra(
                                            "authModes",
                                            ArrayList(getAuthModes(data.authModes))
                                        )
                                        intent.putExtra("abhaId", abhaId)
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
                else {
                    binding.txtLinked.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun checkIfAbhaNumerExists(inputAbhaNumber: String): Boolean {
        val existingABHANumber = Variables.EXISTING_ABHA_NUMBERS
        if (existingABHANumber != null) {
            for (abhaNumber in existingABHANumber) {
                val resultString = abhaNumber.replace("-", "")
                if (resultString == inputAbhaNumber) {
                   return true
                }
            }
        }
        return false;
    }

    private fun getAuthModes(authModes: List<String>): List<String> {

        val items = resources.getStringArray(R.array.menu_items_verify)

        return items.filter { authModes.contains(it) }

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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}