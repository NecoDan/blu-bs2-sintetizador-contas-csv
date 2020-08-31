package br.com.blu.bs2.sintetizador.contas.csv.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @author Daniel Santos
 */
object FormatterUtil {

    private const val MENSAGEM_VALIDACAO = "Parâmetro {DATA}, encontra-se inválida e/ou inexistente {NULL}."

    fun onlyBigDecimal(base: String): BigDecimal {
        return if (Objects.isNull(base) || base.isEmpty()) BigDecimal.ZERO else getOnlyBigDecimal(base)
    }

    private fun getOnlyBigDecimal(base: String): BigDecimal {
        var base = base
        if (base.contains(",")) base = base.replace(',', '.')
        return BigDecimal(base)
    }

    fun toStringLocalDateFormatada(data: LocalDateTime): String {
        if (Objects.nonNull(data)) return data.format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"))
        throw IllegalArgumentException(MENSAGEM_VALIDACAO)
    }

    fun formatarNumero(valor: BigDecimal): String {
        var valor = valor
        valor = valor.setScale(2, RoundingMode.HALF_UP)
        val symbols = DecimalFormatSymbols(Locale("pt", "BR"))
        symbols.decimalSeparator = '.'
        val df = DecimalFormat("#0.00", symbols)
        return df.format(valor)
    }
}
