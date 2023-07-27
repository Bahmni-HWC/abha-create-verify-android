package com.example.abha_create_verify_android.data.model

import com.google.gson.annotations.SerializedName
data class ErrorResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("details")
    val details: List<ErrorDetail>
)

data class ErrorDetail(
    @SerializedName("message")
    val message: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("attribute")
    val attribute: Attribute
)

data class Attribute(
    @SerializedName("key")
    val key: String,
    @SerializedName("value")
    val value: String
)