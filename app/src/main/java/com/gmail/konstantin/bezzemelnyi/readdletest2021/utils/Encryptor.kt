package com.gmail.konstantin.bezzemelnyi.readdletest2021.utils

import java.math.BigInteger
import java.security.MessageDigest

object Encryptor {

    fun encryptStringToMD5(string: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(string.toByteArray())).toString(16).padStart(32, '0')
    }

}