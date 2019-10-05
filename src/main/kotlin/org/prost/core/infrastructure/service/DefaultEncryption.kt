package org.prost.core.infrastructure.service

import org.prost.core.infrastructure.Encryption
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class DefaultEncryption : Encryption {
    override fun encrypt(data: String): String {
        val md = MessageDigest.getInstance("MD5")
        val md5Base16 = BigInteger(1, md.digest(data.toByteArray()))
        return Base64.getEncoder().encodeToString(md5Base16.toByteArray()).trim()
    }
}

fun main() {
    val a = DefaultEncryption()
    println(a.encrypt("1234"))
}