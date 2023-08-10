package com.example.abha_create_verify_android.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtils {
    fun showConfirmationDialog(context: Context, onConfirm: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Go Back")
            .setMessage("Are you certain you wish to return to the patient registration page ?")
            .setPositiveButton("Yes") { _, _ ->
                onConfirm.invoke()
            }
            .setNegativeButton("No", null)
            .show()
    }
}