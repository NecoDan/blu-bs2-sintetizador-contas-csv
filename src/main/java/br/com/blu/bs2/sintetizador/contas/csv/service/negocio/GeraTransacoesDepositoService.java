package br.com.blu.bs2.sintetizador.contas.csv.service.negocio;

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo;
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta;
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.ExtratoConta;
import br.com.blu.bs2.sintetizador.contas.csv.model.enums.TipoExtensaoArquivo;
import br.com.blu.bs2.sintetizador.contas.csv.service.strategy.PercentualBonus;
import br.com.blu.bs2.sintetizador.contas.csv.utils.FormatterUtil;
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GeraTransacoesDepositoService implements IGeraTransacoesDepositoService {

    private static final String HEADER_COL_RELATORIO_POS_0 = "Conta";
    private static final String HEADER_COL_RELATORIO_POS_1 = "Depósitos";
    private static final String HEADER_COL_RELATORIO_POS_2 = "Total de Bônus";

    @Override
    public Arquivo gerarDepositosFromTransacoes(Arquivo arquivo, List<Conta> contaList) throws ServiceException {
        if (Objects.isNull(contaList) || contaList.isEmpty())
            throw new ServiceException("Não existem contas a serem processadas ou a efetuar novas transações de depósitos.");

        List<ExtratoConta> extratoContaList = contaList.stream().filter(Objects::nonNull).map(this::montarExtratoContaFrom).collect(Collectors.toList());
        return salvarExtratosContas(arquivo, extratoContaList);
    }

    @Override
    public Arquivo salvarExtratosContas(Arquivo arquivo, List<ExtratoConta> extratoContaList) throws ServiceException {
        try {
            String nomeArquivo = arquivo.getFileEntrada().getName()
                    .replace(".", "_")
                    .toUpperCase()
                    .concat("_SAIDA_")
                    .concat(FormatterUtil.toStringLocalDateFormatada(LocalDateTime.now()))
                    .concat(".")
                    .concat(TipoExtensaoArquivo.CSV.getCodigoLiteral());

            gravarExtraoContaCSVSaidaRelatorio(arquivo.getFileEntrada().getParent(), nomeArquivo, extratoContaList);
            arquivo.setFileSaida(FileUtils.getFile(nomeArquivo));

            return arquivo;
        } catch (IOException e) {
            throw new ServiceException("Erro ao gerar o arquivo de saída: " + e.getLocalizedMessage());
        }
    }

    private ExtratoConta montarExtratoContaFrom(Conta conta) {
        ExtratoConta extratoConta = ExtratoConta.builder().conta(conta).valorTotalDeposito(conta.getSaldo()).build();
        PercentualBonus percentualBonus = PercentualBonus.of(conta.getDigito());
        extratoConta.aplicarValorPercentualBonus(percentualBonus);
        return extratoConta;
    }

    private void gravarExtraoContaCSVSaidaRelatorio(String pahtCaminhoASalvar, String nomeArquivo, List<ExtratoConta> extratoContaList) throws IOException {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(pahtCaminhoASalvar + "/" + nomeArquivo));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(HEADER_COL_RELATORIO_POS_0, HEADER_COL_RELATORIO_POS_1, HEADER_COL_RELATORIO_POS_2).withDelimiter(';'));
        ) {
            extratoContaList.forEach(extratoConta -> {
                try {
                    csvPrinter.printRecord(
                            extratoConta.getConta().toStringNumeroContaCompleta(),
                            FormatterUtil.formatarNumero(extratoConta.getValorTotalDeposito()),
                            FormatterUtil.formatarNumero(extratoConta.getValorPercentualBonus())
                    );
                } catch (IOException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            });
            csvPrinter.flush();
        }
    }
}
