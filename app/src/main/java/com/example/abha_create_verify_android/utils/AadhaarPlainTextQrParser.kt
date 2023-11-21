package com.example.abha_create_verify_android.utils

import android.util.Log

class AadhaarPlainTextQrParser(scanData: String?) {
    private var scannedAadhaarCardInfo: AadhaarCardInfo = AadhaarCardInfo()

    init {
        processPlainTextData(scanData)
    }

    private fun processPlainTextData(scanData: String?){
        Log.d("Aadhaar Plain text", "Start processing")
        val keyValuePairs = scanData?.split(", ")
        val result = mutableMapOf<String, String>()

        if (keyValuePairs != null) {
            for (pair in keyValuePairs) {
                if(":" in pair) {
                    val (key, value) = pair.split(":")
                    result[key.trim().lowercase()] = value.trim()
                }
            }
        }
        Log.d("Aadhaar Plain text", "Data Processed")

        scannedAadhaarCardInfo.name = result[AadhaarDataAttributes.AADHAAR_NAME_ATTR]
        scannedAadhaarCardInfo.gender = result[AadhaarDataAttributes.AADHAAR_GENDER_ATTR]
        scannedAadhaarCardInfo.dateOfBirth = result[AadhaarDataAttributes.AADHAAR_DOB_ATTR]
        scannedAadhaarCardInfo.villageTownCity = result[AadhaarDataAttributes.AADHAAR_VTC_ATTR]
        scannedAadhaarCardInfo.subDistrict = result[AadhaarDataAttributes.AADHAAR_SUB_DIST_ATTR]
        scannedAadhaarCardInfo.district = result[AadhaarDataAttributes.AADHAAR_DIST_ATTR]
        scannedAadhaarCardInfo.state = result[AadhaarDataAttributes.AADHAAR_STATE_ATTR]
        scannedAadhaarCardInfo.pinCode = result[AadhaarDataAttributes.AADHAAR_PC_ATTR]
    }

    fun getAadhaarCardInfo(): AadhaarCardInfo {
        return scannedAadhaarCardInfo
    }

}

