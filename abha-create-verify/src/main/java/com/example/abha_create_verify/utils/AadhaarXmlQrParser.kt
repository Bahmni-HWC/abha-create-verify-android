package com.example.abha_create_verify.utils

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader


class AadhaarXmlQrParser(scanData: String?) {
    private var scannedAadhaarCardInfo: AadhaarCardInfo = AadhaarCardInfo()

    init {
        processXmlData(scanData)
    }

    private fun processXmlData(scanData: String?) {
        val pullParserFactory: XmlPullParserFactory
        try {
            pullParserFactory = XmlPullParserFactory.newInstance()

            val parser = pullParserFactory.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(StringReader(scanData))

            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("Aadhaar Xml", "Start document")
                } else if (eventType == XmlPullParser.START_TAG && AadhaarDataAttributes.AADHAAR_DATA_TAG == parser.name
                ) {
                    scannedAadhaarCardInfo.name =
                        parser.getAttributeValue(
                            null,
                            AadhaarDataAttributes.AADHAAR_NAME_ATTR
                        )

                    scannedAadhaarCardInfo.gender =
                        parser.getAttributeValue(
                            null,
                            AadhaarDataAttributes.AADHAAR_GENDER_ATTR
                        )

                    scannedAadhaarCardInfo.dateOfBirth =
                        parser.getAttributeValue(
                            null,
                            AadhaarDataAttributes.AADHAAR_DOB_ATTR
                        )?.replace('/', '-')

                    scannedAadhaarCardInfo.villageTownCity =
                        parser.getAttributeValue(
                            null,
                            AadhaarDataAttributes.AADHAAR_VTC_ATTR
                        )

                    scannedAadhaarCardInfo.subDistrict =
                        parser.getAttributeValue(
                            null,
                            AadhaarDataAttributes.AADHAAR_SUB_DIST_ATTR
                        )

                    scannedAadhaarCardInfo.district =
                        parser.getAttributeValue(
                            null,
                            AadhaarDataAttributes.AADHAAR_DIST_ATTR
                        )

                    scannedAadhaarCardInfo.state =
                        parser.getAttributeValue(
                            null,
                            AadhaarDataAttributes.AADHAAR_STATE_ATTR
                        )

                    scannedAadhaarCardInfo.pinCode =
                        parser.getAttributeValue(
                            null,
                            AadhaarDataAttributes.AADHAAR_PC_ATTR
                        )

                } else if (eventType == XmlPullParser.END_TAG) {
                    Log.d("Aadhaar Xml", "End tag " + parser.name)
                } else if (eventType == XmlPullParser.TEXT) {
                    Log.d("Aadhaar Xml", "Text " + parser.text)
                }
                eventType = parser.next()
            }

            return
        } catch (e: XmlPullParserException) {
            Log.e("Exception", "Error in processing XML")
            e.printStackTrace()
            return
        } catch (e: IOException) {
            Log.e("Exception", e.toString())
            e.printStackTrace()
            return
        }
    }

    fun getAadhaarCardInfo(): AadhaarCardInfo {
        return scannedAadhaarCardInfo
    }

}

