package com.example.abha_create_verify_android.verify

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.AbhaAddressActivity
import com.example.abha_create_verify_android.AbhaPatientProfileActivity
import com.example.abha_create_verify_android.MainViewModel
import com.example.abha_create_verify_android.PatientSubject
import com.example.abha_create_verify_android.R
import com.example.abha_create_verify_android.ViewModelFactory
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.data.model.MobileNumberLinkedABHA
import com.example.abha_create_verify_android.data.model.SearchAbhaReq
import com.example.abha_create_verify_android.databinding.ActivityLinkedAbhaBinding
import com.example.abha_create_verify_android.utils.DialogUtils
import com.example.abha_create_verify_android.utils.Status
import com.example.abha_create_verify_android.utils.Variables


class MobileLinkedABHAActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLinkedAbhaBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinkedAbhaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.appBarLayout.includeToolbar.toolbarAbha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.verify_abha)

        val dataList: ArrayList<MobileNumberLinkedABHA>? = intent.getSerializableExtra("linkedABHAs") as? ArrayList<MobileNumberLinkedABHA>

        val listContainer = findViewById<LinearLayout>(R.id.listContainer)

        var isABHAAddressLinked = false
        var selectedIndex = -1
        var selectedTextView: AppCompatTextView? = null

        if (dataList != null) {
            for ((index, dataItem) in dataList.withIndex()) {
                val textView = createTextView(dataItem)

                textView.setOnClickListener {
                    binding.abhaAddressNotLinked.visibility = View.GONE
                    binding.txtLinked.visibility = View.INVISIBLE

                    selectedTextView?.setBackgroundColor(Color.WHITE)
                    textView.setBackgroundResource(R.drawable.border)

                    selectedTextView = textView
                    selectedIndex = index

                    if (dataList[selectedIndex].abhaAddress.isEmpty()) {
                        binding.abhaAddressNotLinked.visibility = View.VISIBLE
                        isABHAAddressLinked = false
                    }
                    else
                    {
                        isABHAAddressLinked = true
                    }
                }

                listContainer.addView(textView)

                val dividerView = createDividerView()
                listContainer.addView(dividerView)

            }
        }


        binding.proceedButton.setOnClickListener {
            if(selectedIndex != -1 && dataList?.get(selectedIndex)  != null) {
                if (Variables.EXISTING_ABHA_NUMBERS?.contains(dataList[selectedIndex].abhaNumber) == true) {
                    binding.txtLinked.visibility = View.VISIBLE
                } else {
                    viewModel.getAbhaProile(SearchAbhaReq(dataList[selectedIndex].abhaNumber))
                    .observe(this) {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    binding.progressBar.visibility = View.GONE
                                    resource.data?.let { data ->
                                        PatientSubject().setPatient(data)
                                        val intent = Intent(
                                            this,
                                            if (isABHAAddressLinked) AbhaPatientProfileActivity::class.java else AbhaAddressActivity::class.java
                                        )
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


        binding.appBarLayout.includeToolbar.customCloseButton.setOnClickListener { v ->
            DialogUtils.showConfirmationDialog(this) {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun createDividerView(): View {
        val dividerView = View(this)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            resources.getDimensionPixelSize(R.dimen.divider_height)
        )
        params.marginStart = resources.getDimensionPixelSize(R.dimen.divider_margin_start)
        params.marginEnd = resources.getDimensionPixelSize(R.dimen.divider_margin_end)

        dividerView.layoutParams = params
        dividerView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray))
        return dividerView
    }

    private fun createTextView(dataItem: MobileNumberLinkedABHA): AppCompatTextView {
        val textView = AppCompatTextView(this)
        val text = "${dataItem.name}\n${dataItem.abhaNumber}\n${dataItem.abhaAddress}"

        val spannableString = SpannableString(text)

        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            dataItem.name.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            RelativeSizeSpan(0.8f),
            dataItem.name.length + 1,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannableString
        textView.typeface = ResourcesCompat.getFont(this, R.font.roboto)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        textView.setTextColor(ContextCompat.getColor(this, R.color.black))
        textView.gravity = Gravity.CENTER_VERTICAL or Gravity.START
        textView.setPadding(10, 10, 10, 20)
        textView.setBackgroundColor(Color.WHITE)
        textView.isClickable = true

        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.user, 0, 0, 0)
        return textView
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
        val intent = Intent(this, VerifyMobileNumberActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun openMobileNumberVerification(view: View) {
        val intent = Intent(this, VerifyOTPActivity::class.java)
        startActivity(intent)
        finish()
    }


}
