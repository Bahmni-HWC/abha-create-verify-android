package com.example.abha_create_verify_android.data.model

import com.google.gson.annotations.SerializedName

data class ConfirmOtpResp(
    @SerializedName("healthIdNumber")
    val healthIdNumber: String? = null,

    @SerializedName("healthId")
    val healthId: String? = null,

    @SerializedName("profilePhoto")
    val profilePhoto: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("firstName")
    val firstName: String? = null,

    @SerializedName("middleName")
    val middleName: String? = null,

    @SerializedName("lastName")
    val lastName: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("dayOfBirth")
    val dayOfBirth: String? = null,

    @SerializedName("monthOfBirth")
    val monthOfBirth: String? = null,

    @SerializedName("yearOfBirth")
    val yearOfBirth: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("districtName")
    val districtName: String? = null,

    @SerializedName("stateName")
    val stateName: String? = null,

    @SerializedName("subDistrictName")
    val subDistrictName: String? = null,

    @SerializedName("townName")
    val townName: String? = null,

    @SerializedName("villageName")
    val villageName: String? = null,

    @SerializedName("wardName")
    val wardName: String? = null,

    @SerializedName("pincode")
    val pincode: String? = null,

    @SerializedName("mobile")
    val mobile: String? = null
)