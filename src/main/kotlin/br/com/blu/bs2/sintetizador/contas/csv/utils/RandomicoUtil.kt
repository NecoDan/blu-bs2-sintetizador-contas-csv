package br.com.blu.bs2.sintetizador.contas.csv.utils

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
}
