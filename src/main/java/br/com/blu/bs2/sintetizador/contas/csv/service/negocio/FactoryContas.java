package br.com.blu.bs2.sintetizador.contas.csv.service.negocio;

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo;
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta;
import br.com.blu.bs2.sintetizador.contas.csv.utils.FormatterUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FactoryContas implements IFactoryContas {

    private static final String SEPARADOR_DEFAULT_CONTA = "-";
    private static final String POSICAO_NUMERO_CONTA = "0";
    private static final String POSICAO_DIGITO_VERIFICADOR_CONTA = "1";

    @Override
    public List<Conta> getContasPorArquivoEFileCSVReader(Arquivo arquivo) throws IOException {
        List<Conta> contaList = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(arquivo.getPathCompleto()));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withDelimiter(';').withIgnoreHeaderCase().withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                Conta conta = montarContaFrom(csvRecord);

                if (Objects.isNull(conta))
                    continue;

                BigDecimal valor = FormatterUtil.onlyBigDecimal(csvRecord.get(1));
                contaList.add(getContaExistenteFromList(contaList, conta, valor));
            }
        }

        return contaList.stream().filter(Objects::nonNull).sorted(Comparator.comparing(Conta::getNumero)).distinct().collect(Collectors.toList());
    }

    @Override
    public Conta getContaExistenteFromList(List<Conta> contaList, Conta conta, BigDecimal valor) {
        Optional<Conta> optionalConta = getOptionalContaExistente(contaList, conta);
        return optionalConta.map(c -> getContaResultAdicionaValorSaldo(c, valor)).orElseGet(() -> getContaResultAdicionaValorSaldo(conta, valor));
    }

    @Override
    public Optional<Conta> getOptionalContaExistente(List<Conta> contaList, Conta conta) {
        return contaList.stream().filter(Objects::nonNull).filter(c -> c.toStringNumeroContaCompleta().equals(conta.toStringNumeroContaCompleta())).findFirst();
    }

    private Conta getContaResultAdicionaValorSaldo(Conta conta, BigDecimal valor) {
        conta.addSaldo(valor);
        return conta;
    }

    private Conta montarContaFrom(CSVRecord csvRecord) {
        String strNumeroConta = csvRecord.get(0);

        List<String> linhasRegistro = new ArrayList<>(Arrays.asList(strNumeroConta.split(SEPARADOR_DEFAULT_CONTA)));
        Map<String, String> mapParameters = getMapParametersFromListaRegistros(linhasRegistro);

        Long numeroConta = Long.valueOf(mapParameters.get(POSICAO_NUMERO_CONTA));
        int digitoVerificador = Integer.parseInt(mapParameters.get(POSICAO_DIGITO_VERIFICADOR_CONTA));

        return Conta.builder().numero(numeroConta).digito(digitoVerificador).build().geraTipoConta();
    }

    private Map<String, String> getMapParametersFromListaRegistros(List<String> linhasRegistros) {
        return getStringStringMap(linhasRegistros, POSICAO_NUMERO_CONTA, POSICAO_DIGITO_VERIFICADOR_CONTA);
    }

    private String escreveMap(Map<String, String> mapParameters, int index, String posicao, String value) {
        return (isParamsPermiteEscritaMapParameters(index, posicao)) ? mapParameters.put(posicao, value) : mapParameters.put("", "");
    }

    private boolean isParamsPermiteEscritaMapParameters(int index, String posicao) {
        return (index >= 0 && Objects.nonNull(posicao) && String.valueOf(index).equals(posicao));
    }

    private Map<String, String> getStringStringMap(List<String> linhasRegistro, String posicaoA, String posicaoB) {
        Map<String, String> mapParameters = new HashMap<>();

        for (int i = 0; i < linhasRegistro.size(); i++) {
            escreveMap(mapParameters, i, posicaoA, linhasRegistro.get(i));
            escreveMap(mapParameters, i, posicaoB, linhasRegistro.get(i));
        }

        return mapParameters;
    }


}
