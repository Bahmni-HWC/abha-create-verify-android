package com.example.abha_create_verify_android.data.model

import com.google.gson.annotations.SerializedName

data class VerifyAbhaPatient(
    @SerializedName("healthIdNumber")
    val abhaNumber: String,

    @SerializedName("healthId")
    val abhaAddress: String,

    @SerializedName("profilePhoto")
    val profilePhoto: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("middleName")
    val middleName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("dayOfBirth")
    val dayOfBirth: String,

    @SerializedName("monthOfBirth")
    val monthOfBirth: String,

    @SerializedName("yearOfBirth")
    val yearOfBirth: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("districtName")
    val districtName: String,

    @SerializedName("stateName")
    val stateName: String,

    @SerializedName("subDistrictName")
    val subDistrictName: String,

    @SerializedName("townName")
    val townName: String,

    @SerializedName("villageName")
    val villageName: String,

    @SerializedName("wardName")
    val wardName: String,

    @SerializedName("pincode")
    val pincode: String,

    @SerializedName("mobile")
    val mobile: String? = null
)