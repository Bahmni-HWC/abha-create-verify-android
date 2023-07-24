package com.example.abha_create_verify.data.model

import com.google.gson.annotations.SerializedName

data class GenerateMobileOTPReq(
    @SerializedName("mobile")
    val mobile: String
)
