package com.example.abha_create_verify_android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.abha_create_verify_android.data.model.AuthInitReq
import com.example.abha_create_verify_android.data.model.ConfirmOtpReq
import com.example.abha_create_verify_android.data.model.CreateAbhaAddressReq
import com.example.abha_create_verify_android.data.model.ErrorResponse
import com.example.abha_create_verify_android.data.model.GenerateAadhaarOTPReq
import com.example.abha_create_verify_android.data.model.GenerateMobileOTPReq
import com.example.abha_create_verify_android.data.model.SearchAbhaReq
import com.example.abha_create_verify_android.data.model.VerifyOTPReq
import com.example.abha_create_verify_android.data.repository.MainRepository
import com.example.abha_create_verify_android.utils.PatientDemographics
import com.example.abha_create_verify_android.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {


    private fun handleErrorResponse(errorBody: ResponseBody?) : String {
        val gson = Gson()
        val jsonString = errorBody?.string()
        val error = gson.fromJson(jsonString, ErrorResponse::class.java)
        return if(error?.details?.isNotEmpty() == true) error.details[0].message else "Error Occurred!"
    }


    fun generateAadhaarOtp(generateAadhaarOTPReq: GenerateAadhaarOTPReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.generateAadhaarOtp(generateAadhaarOTPReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
               val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun verifyAadhaarOtp(verifyOTPReq: VerifyOTPReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.verifyAadhaarOtp(verifyOTPReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun checkAndGenerateMobileOtp(generateMobileOTPReq: GenerateMobileOTPReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.checkAndGenerateMobileOtp(generateMobileOTPReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun verifyMobileOtp(verifyOTPReq: VerifyOTPReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.verifyMobileOtp(verifyOTPReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun createHealthIdByAadhaarOtp() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.createHealthIdByAdhaarOtp()
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun createAbhaAddress(createAbhaAddressReq : CreateAbhaAddressReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.createAbhaAddress(createAbhaAddressReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun createDefaultAbhaAddress() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.createDefaultAbhaAddress()
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun searchAbhaId(searchAbhaReq: SearchAbhaReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.searchAbhaId(searchAbhaReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun authInit(authInitReq: AuthInitReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.authInit(authInitReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun confirmOtp(confirmOtpReq: ConfirmOtpReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.confirmOtp(confirmOtpReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun generateMobileOtp(generateMobileOTPReq: GenerateMobileOTPReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.generateMobileOtp(generateMobileOTPReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun verifyOtp(verifyOTPReq: VerifyOTPReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.verifyOtp(verifyOTPReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getAbhaProile(abhaReq: SearchAbhaReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.getABHAProfile(abhaReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun addPatientDemographics(patientDemographics: PatientDemographics) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.addPatientDemographics(patientDemographics)
            if(res.code() == 200)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}