package com.example.abha_create_verify.data.model

import com.google.gson.annotations.SerializedName

data class CreateAbhaAddressReq(
    @SerializedName("phrAddress")
    val phrAddress: String,
    @SerializedName("preferred")
    val preferred: String
)
