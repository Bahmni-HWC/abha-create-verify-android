package com.example.abha_create_verify_android.data.api

import com.example.abha_create_verify_android.data.model.AuthInitReq
import com.example.abha_create_verify_android.data.model.ConfirmOtpReq
import com.example.abha_create_verify_android.data.model.VerifyAbhaPatient
import com.example.abha_create_verify_android.data.model.CreateABHAResp
import com.example.abha_create_verify_android.data.model.CreateAbhaAddressReq
import com.example.abha_create_verify_android.data.model.CreateDefaultAbhaAddressResp
import com.example.abha_create_verify_android.data.model.GenerateAadhaarOTPReq
import com.example.abha_create_verify_android.data.model.GenerateAadhaarOTPResp
import com.example.abha_create_verify_android.data.model.GenerateMobileOTPReq
import com.example.abha_create_verify_android.data.model.GenerateMobileOTPResp
import com.example.abha_create_verify_android.data.model.MobileNumberLinkedABHA
import com.example.abha_create_verify_android.data.model.SearchAbhaReq
import com.example.abha_create_verify_android.data.model.SearchAbhaResp
import com.example.abha_create_verify_android.data.model.VerifyAadhaarOTPResp
import com.example.abha_create_verify_android.data.model.VerifyOTPReq
import com.example.abha_create_verify_android.utils.PatientDemographics
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("hiprovider/v2/registration/aadhaar/generateOtp")
    suspend fun generateAadhaarOtp(@Body requestBody: GenerateAadhaarOTPReq): Response<GenerateAadhaarOTPResp>

    @POST("hiprovider/v2/registration/aadhaar/verifyOTP")
    suspend fun verifyAadhaarOtp(@Body requestBody: VerifyOTPReq): Response<VerifyAadhaarOTPResp>

    @POST("hiprovider/v2/registration/aadhaar/checkAndGenerateMobileOTP")
    suspend fun checkAndGenerateMobileOtp(@Body requestBody: GenerateMobileOTPReq): Response<GenerateMobileOTPResp>

    @POST("hiprovider/v2/registration/aadhaar/verifyMobileOTP")
    suspend fun verifyMobileOtp(@Body requestBody: VerifyOTPReq) : Response<Unit>

    @POST("hiprovider/v2/registration/aadhaar/createHealthIdByAdhaar")
    suspend fun createHealthIdByAdhaarOtp(): Response<CreateABHAResp>

    @POST("hiprovider/v2/account/phr-linked")
    suspend fun createAbhaAddress(@Body requestBody: CreateAbhaAddressReq): Response<Unit>

    @GET("hiprovider/v1/account/update/phr-address")
    suspend fun createDefaultAbhaAddress(): Response<CreateDefaultAbhaAddressResp>

    @POST("hiprovider/v2/search/searchHealthIdToLogin")
    suspend fun searchAbhaId(@Body requestBody: SearchAbhaReq): Response<SearchAbhaResp>

    @POST("hiprovider/v2/auth/init")
    suspend fun authInit(@Body requestBody: AuthInitReq): Response<Unit>

    @POST("hiprovider/v2/hip/confirmOTP")
    suspend fun confirmOtp(@Body requestBody: ConfirmOtpReq): Response<VerifyAbhaPatient>

    @POST("hiprovider/v2/registration/mobile/login/generateOtp")
    suspend fun generateMobileOtp(@Body requestBody: GenerateMobileOTPReq): Response<Unit>

    @POST("hiprovider/v2/registration/mobile/login/verifyOtp")
    suspend fun verifyOtp(@Body requestBody: VerifyOTPReq): Response<List<MobileNumberLinkedABHA>>

    @POST("hiprovider/v2/registration/mobile/login/userAuthorizedToken")
    suspend fun getABHAProfile(@Body requestBody: SearchAbhaReq): Response<VerifyAbhaPatient>

    @POST("hiprovider/v0.5/hip/ndhm-demographics")
    suspend fun addPatientDemographics(@Body requestBody: PatientDemographics): Response<Unit>

}