package br.com.blu.bs2.sintetizador.contas.csv.service.utils;

import br.com.blu.bs2.sintetizador.contas.csv.service.model.Transacao;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class ParametrosTestesUtil {

    private ParametrosTestesUtil() {
    }

    public static String getDiretorioTempSistema() {
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        System.out.println("Diretorio temporario: " + tempDir);
        return tempDir;
    }

    public static File getFileDiretorioPadraoSistema() {
        return new File(getDiretorioTempSistema());
    }

    public static Transacao mountTransacaoFrom(String numeroConta, BigDecimal valor) {
        return Transacao.builder().numeroConta(numeroConta).valorTransacao(valor).build();
    }

    public static List<Transacao> getListaArquivosTransacao() {
        return Arrays.asList(
                mountTransacaoFrom("1234-3", BigDecimal.valueOf(75)),
                mountTransacaoFrom("1234-3", BigDecimal.valueOf(25)),
                mountTransacaoFrom("1235-6", BigDecimal.valueOf(100)),
                mountTransacaoFrom("1236-0", BigDecimal.valueOf(100))
        );
    }

    private static boolean isPathCaminhoASalvarInvalido(String pahtCaminhoASalvar) {
        return (Objects.isNull(pahtCaminhoASalvar) || pahtCaminhoASalvar.isEmpty());
    }

    public static void salvarGravarCSV(String pahtCaminhoASalvar, String nomeArquivo, List<Transacao> transacaoList) throws IOException {
        try (
                BufferedWriter writer = Files
                        .newBufferedWriter(Paths.get(isPathCaminhoASalvarInvalido(pahtCaminhoASalvar) ? getDiretorioTempSistema() : pahtCaminhoASalvar + "/" + nomeArquivo));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Conta", "Valor").withDelimiter(';'));
        ) {
            transacaoList.forEach(transacao -> {
                try {
                    csvPrinter.printRecord(transacao.getNumeroConta(), String.valueOf(transacao.getValorTransacao()));
                } catch (IOException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            });
            csvPrinter.flush();
        }
    }
}
