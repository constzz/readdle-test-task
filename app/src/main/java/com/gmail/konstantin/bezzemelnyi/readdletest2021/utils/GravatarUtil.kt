package com.gmail.konstantin.bezzemelnyi.readdletest2021.utils

import android.net.Uri

object GravatarUtil {

    fun getProfileImageLink(email: String): String {
        val imageUri = Uri.Builder()
            .scheme("https")
            .authority("www.gravatar.com")
            .appendPath("avatar")
            .appendPath(Encryptor.encryptStringToMD5(email) + ".jpg")
            .build()
        return imageUri.toString()
    }

}