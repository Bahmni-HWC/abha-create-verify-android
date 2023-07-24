package com.example.abha_create_verify_android.data.repository

import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.model.AuthInitReq
import com.example.abha_create_verify_android.data.model.ConfirmOtpReq
import com.example.abha_create_verify_android.data.model.CreateAbhaAddressReq
import com.example.abha_create_verify_android.data.model.GenerateAadhaarOTPReq
import com.example.abha_create_verify_android.data.model.GenerateMobileOTPReq
import com.example.abha_create_verify_android.data.model.SearchAbhaReq
import com.example.abha_create_verify_android.data.model.VerifyOTPReq

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun generateAadhaarOtp(generateAadhaarOTPReq: GenerateAadhaarOTPReq) = apiHelper.generateAadhaarOtp(generateAadhaarOTPReq)

    suspend fun verifyAadhaarOtp(verifyOTPReq: VerifyOTPReq) = apiHelper.verifyAadhaarOtp(verifyOTPReq)

    suspend fun checkAndGenerateMobileOtp(generateMobileOTPReq: GenerateMobileOTPReq) = apiHelper.checkAndGenerateMobileOtp(generateMobileOTPReq)

    suspend fun verifyMobileOtp(verifyOTPReq: VerifyOTPReq) = apiHelper.verifyMobileOtp(verifyOTPReq)

    suspend fun createHealthIdByAdhaarOtp() = apiHelper.createHealthIdByAdhaarOtp()

    suspend fun createAbhaAddress(createAbhaAddressReq : CreateAbhaAddressReq) = apiHelper.createAbhaAddress(createAbhaAddressReq)

    suspend fun createDefaultAbhaAddress() = apiHelper.createDefaultAbhaAddress()

    suspend fun searchAbhaId(searchAbhaReq: SearchAbhaReq) = apiHelper.searchAbhaId(searchAbhaReq)

    suspend fun authInit(authInitReq: AuthInitReq) = apiHelper.authInit(authInitReq)

    suspend fun confirmOtp(confirmOtpReq: ConfirmOtpReq) = apiHelper.confirmOtp(confirmOtpReq)
}