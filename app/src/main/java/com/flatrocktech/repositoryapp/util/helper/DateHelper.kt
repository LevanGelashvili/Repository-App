package com.flatrocktech.repositoryapp.util.helper

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    fun formatDate(
        dateString: String?,
        pattern: String = "yyyy-MM-dd'T'HH:mm:ssX"
    ): String? {
        val date = dateString?.let {
            SimpleDateFormat(pattern, Locale.US).parse(dateString)
        }
        return date?.let {
            SimpleDateFormat("yyyy/MM/DD", Locale.US).format(it)
        }
    }
}