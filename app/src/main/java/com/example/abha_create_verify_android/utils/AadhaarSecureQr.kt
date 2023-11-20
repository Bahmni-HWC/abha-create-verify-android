package com.example.abha_create_verify_android.utils

import android.util.Log
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.util.LinkedList
import java.util.zip.GZIPInputStream


class AadhaarSecureQr(scanData: String?) {
    private var decodedData: ArrayList<String>? = null
    private var scannedAadhaarCard: AadhaarCard = AadhaarCard()

    init {

        val bigIntScanData = BigInteger(scanData.toString(), 10)
        val byteScanData = bigIntScanData.toByteArray()
        val decompressByteScanData = decompressData(byteScanData)

        val parts = separateData(decompressByteScanData)
        if (parts.isEmpty()) {
            throw QrCodeException("Invalid QR Code Data, no parts found after splitting by delimiter")
        }
        decodeData(parts)

        Log.d("Aadhaar QR", "Data Decoded")
    }

    @Throws(QrCodeException::class)
    private fun decompressData(byteScanData: ByteArray): ByteArray {
        val bos = ByteArrayOutputStream(byteScanData.size)
        val bin = ByteArrayInputStream(byteScanData)
        val gis: GZIPInputStream? = try {
            GZIPInputStream(bin)
        } catch (e: IOException) {
            Log.e("Exception", "Decompressing QR code, Opening byte stream failed: $e")
            throw QrCodeException("Error in opening Gzip byte stream while decompressing QR code", e)
        }
        var size = 0
        val buf = ByteArray(1024)
        while (size >= 0) {
            try {
                size = gis?.read(buf, 0, buf.size) ?: 0
                if (size > 0) {
                    bos.write(buf, 0, size)
                }
            } catch (e: IOException) {
                Log.e(
                    "Exception",
                    "Decompressing QR code, writing byte stream failed: $e"
                )
                throw QrCodeException("Error in writing byte stream while decompressing QR code", e)
            }
        }
        try {
            gis?.close()
            bin.close()
        } catch (e: IOException) {
            Log.e("Exception", "Decompressing QR code, closing byte stream failed: $e")
            throw QrCodeException("Error in closing byte stream while decompressing QR code", e)
        }
        return bos.toByteArray()
    }


    private fun separateData(source: ByteArray): List<ByteArray> {
        val separatorByte = 255.toByte()
        val separatedParts: MutableList<ByteArray> = LinkedList()
        var begin = 0
        for (i in source.indices) {
            if (source[i] == separatorByte) {
                // skip if first or last byte is separator
                if (i != 0 && i != (source.size - 1)) {
                    separatedParts.add(source.copyOfRange(begin, i))
                }
                begin = i + 1
            }
        }
        return separatedParts
    }


    @Throws(QrCodeException::class)
    private fun decodeData(encodedData: List<ByteArray>) {
        val i = encodedData.iterator()
        decodedData = ArrayList()
        while (i.hasNext()) {
            try {
                decodedData!!.add(String(i.next(), Charsets.ISO_8859_1))

            } catch (e: UnsupportedEncodingException) {
                Log.e("Exception", "Decoding QR code, ISO-8859-1 not supported: $e")
                throw QrCodeException("Decoding QR code, ISO-8859-1 not supported", e)
            }
        }
        var index=0
        if(decodedData!![0].equals("V2")){
          index=1
        }

        scannedAadhaarCard.name = decodedData!![2 + index]
        scannedAadhaarCard.dateOfBirth = decodedData!![3 + index]
        scannedAadhaarCard.gender = decodedData!![4 + index]
        scannedAadhaarCard.district = decodedData!![6 + index]
        scannedAadhaarCard.pinCode = decodedData!![10 + index]
        scannedAadhaarCard.state = decodedData!![12 + index]
        scannedAadhaarCard.last4DigitAadhaar = decodedData!![1 + index].substring(0, 4)
    }

    fun getScannedAadhaarInfo(): AadhaarCard {
        return scannedAadhaarCard
    }
}

internal class QrCodeException : Exception {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
