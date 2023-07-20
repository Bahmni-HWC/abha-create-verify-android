package com.example.abha_create_verify_android

import com.example.abha_create_verify_android.utils.Patient
import com.example.abha_create_verify_android.data.model.VerifyAadhaarOTPResp
import java.text.SimpleDateFormat
import java.util.Date

class PatientSubject {


    fun setDemographics(patient: VerifyAadhaarOTPResp){
        patientSubject.name = patient.fullName
        separateFullName(patient.fullName)
        patientSubject.dateOfBirth = formatDateOfBirth(patient.birthdate)
        patientSubject.gender = convertGenderAbbreviationToFull(patient.gender)
        patientSubject.villageTownCity = patient.villageTownCity
        patientSubject.address = getAddress(patient)
    }

    private fun formatDateOfBirth(dateOfBirth : String): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat = SimpleDateFormat("dd/MM/yyyy")
        val date: Date = inputFormat.parse(dateOfBirth)
        return outputFormat.format(date)
    }

    private fun getAddress(patient: VerifyAadhaarOTPResp): String {
        val addressString = StringBuilder()
        listOfNotNull(
            patient.house,
            patient.street,
            patient.villageTownCity,
            patient.district,
            patient.state,
            patient.pincode
        ).forEach { str ->
            if (addressString.isNotEmpty()) {
                addressString.append(", ")
            }
            addressString.append(str)
        }
        return addressString.toString()
    }

    fun setABHANumber(abhaNumber: String){
        patientSubject.abhaNumber = abhaNumber
    }

    fun setMobile(mobile: String){
        patientSubject.phoneNumber = mobile
    }

    fun setABHAAddress(abhaAddress: String){
        patientSubject.abhaAddress = abhaAddress
    }
    private fun separateFullName(fullName: String) {
        val parts = fullName.split(" ")
        patientSubject.firstName = parts.subList(0, parts.size - 1).joinToString(" ")
        patientSubject.lastName = parts.last()
    }

    private fun convertGenderAbbreviationToFull(genderAbbreviation: String): String {
        return when (genderAbbreviation.toUpperCase()) {
            "F" -> "Female"
            "M" -> "Male"
            else -> "Other" // Return the original value if not "F" or "M"
        }
    }

    companion object {
        var patientSubject = Patient()
    }
}