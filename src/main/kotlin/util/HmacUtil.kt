package dev.anton.util

import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object HmacUtil {

    private const val HMAC_ALGO = "HmacSHA256"

    private val secretKey = "Jeg[e}Mts|;;ZD&Jv2_J5#zMK25(%~aEC0|I,I#u%P13HEr7,lx3y1kPe9DSD>Gp2".toByteArray()

    private fun hmac(data: ByteArray): ByteArray {
        val mac = Mac.getInstance(HMAC_ALGO)
        mac.init(SecretKeySpec(secretKey, HMAC_ALGO))
        return mac.doFinal(data)
    }

    fun createToken(rawData: String): String {
        val payloadBase64 = Base64.getUrlEncoder().withoutPadding()
            .encodeToString(rawData.toByteArray())

        val signature = hmac(payloadBase64.toByteArray())
        val signatureBase64 = Base64.getUrlEncoder().withoutPadding()
            .encodeToString(signature)

        return "$payloadBase64.$signatureBase64"
    }

    fun validateToken(token: String, expectedSubstring: String? = null): Boolean {
        val parts = token.split(".")
        if (parts.size != 2) return false

        val payloadBase64 = parts[0]
        val signatureBase64 = parts[1]

        val expectedSignature = Base64.getUrlEncoder().withoutPadding()
            .encodeToString(hmac(payloadBase64.toByteArray()))

        if (signatureBase64 != expectedSignature) return false

        val decoded = String(Base64.getUrlDecoder().decode(payloadBase64))

        return !(expectedSubstring != null && !decoded.contains(expectedSubstring))
    }
}