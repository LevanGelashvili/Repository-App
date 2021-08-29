package com.flatrocktech.repositoryapp.util.helper

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    /*
        I had to check and remove last character 'Z' from dateString,
        because requested minSdkRequirement (Android 6 and above)
        can not support automatic parsing of ISO 8601 dateStrings
     */
    fun formatDate(
        dateString: String?,
        pattern: String = "yyyy-MM-dd'T'HH:mm:ss"
    ): String? {
        val date = dateString?.let {
            val modifiedDateString = if (it.last() == 'Z') {
                it.dropLast(1)
            } else {
                it
            }
            SimpleDateFormat(pattern, Locale.US).parse(modifiedDateString)
        }
        return date?.let {
            SimpleDateFormat("yyyy/MM/dd", Locale.US).format(it)
        }
    }
}