package br.com.blu.bs2.sintetizador.contas.csv.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Daniel Santos
 */
public final class FormatterUtil {

    private static final String MENSAGEM_VALIDACAO = "Parâmetro {DATA}, encontra-se inválida e/ou inexistente {NULL}.";

    private FormatterUtil() {
    }

    public static BigDecimal onlyBigDecimal(String base) {
        return (Objects.isNull(base) || base.isEmpty()) ? BigDecimal.ZERO : getOnlyBigDecimal(base);
    }

    private static BigDecimal getOnlyBigDecimal(String base) {
        if (base.contains(","))
            base = base.replace(',', '.');
        return new BigDecimal(base);
    }

    public static String toStringLocalDateFormatada(LocalDateTime data) {
        if (Objects.nonNull(data))
            return data.format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"));
        throw new IllegalArgumentException(MENSAGEM_VALIDACAO);
    }

    public static String formatarNumero(BigDecimal valor) {
        valor = valor.setScale(2, RoundingMode.HALF_UP);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator('.');

        DecimalFormat df = new DecimalFormat("#0.00", symbols);
        return df.format(valor);
    }
}

