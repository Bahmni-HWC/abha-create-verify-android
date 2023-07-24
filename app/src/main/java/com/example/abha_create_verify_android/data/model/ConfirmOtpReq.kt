package com.example.abha_create_verify_android.data.model

import com.google.gson.annotations.SerializedName

data class ConfirmOtpReq(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("authMethod")
    val authMode: String
)
