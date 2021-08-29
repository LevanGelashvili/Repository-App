package com.flatrocktech.repositoryapp.util.helper

import android.content.Context
import android.content.Intent
import android.net.Uri

object BrowserHelper {

    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }
}