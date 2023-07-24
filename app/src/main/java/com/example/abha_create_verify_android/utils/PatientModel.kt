package com.example.abha_create_verify_android.utils

import com.google.gson.annotations.SerializedName
data class Patient(
    @SerializedName("abhaAddress")
    var abhaAddress: String? = "",
    @SerializedName("abhaNumber")
    var abhaNumber: String? = "",
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("dateOfBirth")
    var dateOfBirth: String? = "",
    @SerializedName("firstName")
    var firstName: String? = "",
    @SerializedName("gender")
    var gender: String? = "",
    @SerializedName("lastName")
    var lastName: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("phoneNumber")
    var phoneNumber: String? = "",
    @SerializedName("villageTownCity")
    var villageTownCity: String? = ""
)