package br.com.blu.bs2.sintetizador.contas.csv.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * @author Daniel Santos
 */
object RandomicoUtil {

    private const val LIMITE_MAX_RANDOMICO_INTEIRO = 500000

    private val random: Random
        get() = Random()

    fun gerarValorRandomico(): Int {
        val min = 1
        return min + random.nextInt(LIMITE_MAX_RANDOMICO_INTEIRO)
    }

    fun gerarValorRandomicoLong(): Long {
        return gerarValorRandomico().toLong()
    }

    fun gerarValorRandomicoDecimal(): BigDecimal? {
        val leftLimit = 1.0
        val rightLimit = 10000.0
        return BigDecimal.valueOf(leftLimit + random.nextDouble() * (rightLimit - leftLimit)).setScale(2, RoundingMode.HALF_UP)
    }
}
