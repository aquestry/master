package dev.anton.util

import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object HmacUtil {
    private const val A = "HmacSHA256"
    private val K = "Jeg[e}Mts|;;ZD&Jv2_J5#zMK25(%~aEC0|I,I#u%P13HEr7,lx3y1kPe9DSD>Gp2".toByteArray()

    private fun h(d: ByteArray) =
        Mac.getInstance(A).apply { init(SecretKeySpec(K, A)) }.doFinal(d)

    fun create(raw: String): String {
        val p = Base64.getUrlEncoder().withoutPadding().encodeToString(raw.toByteArray())
        val s = Base64.getUrlEncoder().withoutPadding().encodeToString(h(p.toByteArray()))
        return "$p.$s"
    }

    fun verify(token: String): String? {
        val parts = token.split(".")
        if (parts.size != 2) return null
        val p = parts[0]
        val s = parts[1]
        val e = Base64.getUrlEncoder().withoutPadding().encodeToString(h(p.toByteArray()))
        if (s != e) return null
        return String(Base64.getUrlDecoder().decode(p))
    }
}