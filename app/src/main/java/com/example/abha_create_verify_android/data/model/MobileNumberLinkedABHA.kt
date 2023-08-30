package com.example.abha_create_verify_android.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MobileNumberLinkedABHA (
    @SerializedName("healthIdNumber")
    val abhaNumber: String,

    @SerializedName("healthId")
    val abhaAddress: String,

    @SerializedName("name")
    val name: String
): Serializable