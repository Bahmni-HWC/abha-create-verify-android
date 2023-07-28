package com.example.abha_create_verify.data.api

import com.example.abha_create_verify.data.model.AuthInitReq
import com.example.abha_create_verify.data.model.ConfirmOtpReq
import com.example.abha_create_verify.data.model.CreateAbhaAddressReq
import com.example.abha_create_verify.data.model.GenerateAadhaarOTPReq
import com.example.abha_create_verify.data.model.GenerateMobileOTPReq
import com.example.abha_create_verify.data.model.SearchAbhaReq
import com.example.abha_create_verify.data.model.VerifyOTPReq
import com.example.abha_create_verify.utils.PatientDemographics

class ApiHelper(private val apiService: ApiService) {

    suspend fun generateAadhaarOtp(generateAadhaarOTPReq: GenerateAadhaarOTPReq) = apiService.generateAadhaarOtp(generateAadhaarOTPReq)

    suspend fun verifyAadhaarOtp(verifyOTPReq: VerifyOTPReq) = apiService.verifyAadhaarOtp(verifyOTPReq)

    suspend fun checkAndGenerateMobileOtp(generateMobileOTPReq: GenerateMobileOTPReq) = apiService.checkAndGenerateMobileOtp(generateMobileOTPReq)

    suspend fun verifyMobileOtp(verifyOTPReq: VerifyOTPReq) = apiService.verifyMobileOtp(verifyOTPReq)

    suspend fun createHealthIdByAdhaarOtp() = apiService.createHealthIdByAdhaarOtp()

    suspend fun createAbhaAddress(createAbhaAddressReq : CreateAbhaAddressReq) = apiService.createAbhaAddress(createAbhaAddressReq)

    suspend fun createDefaultAbhaAddress() = apiService.createDefaultAbhaAddress()

    suspend fun searchAbhaId(searchAbhaReq: SearchAbhaReq) = apiService.searchAbhaId(searchAbhaReq)

    suspend fun authInit(authInitReq: AuthInitReq) = apiService.authInit(authInitReq)

    suspend fun confirmOtp(confirmOtpReq: ConfirmOtpReq) = apiService.confirmOtp(confirmOtpReq)
    suspend fun addPatientDemographics(patientDemographics: PatientDemographics) = apiService.addPatientDemographics(patientDemographics)
}