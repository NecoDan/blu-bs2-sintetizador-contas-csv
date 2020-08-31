package br.com.blu.bs2.sintetizador.contas.csv.service.negocio

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta
import br.com.blu.bs2.sintetizador.contas.csv.utils.FormatterUtil.onlyBigDecimal
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.io.IOException
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors

class FactoryContas : IFactoryContas {
    @Throws(IOException::class)
    override fun getContasPorArquivoEFileCSVReader(arquivo: Arquivo?): List<Conta?>? {

        val contaList: MutableList<Conta?> = ArrayList()

        Files.newBufferedReader(Paths.get(arquivo!!.pathCompleto)).use { reader ->
            CSVParser(reader, CSVFormat.DEFAULT.withHeader().withDelimiter(';').withIgnoreHeaderCase().withTrim()).use { csvParser ->
                for (csvRecord in csvParser) {
                    val conta = montarContaFrom(csvRecord)
                    if (Objects.isNull(conta)) continue
                    val valor = onlyBigDecimal(csvRecord[1])
                    contaList.add(getContaExistenteFromList(contaList, conta, valor))
                }
            }
        }

        return contaList.stream().filter { obj: Conta? -> Objects.nonNull(obj) }.distinct().collect(Collectors.toList())
    }

    override fun getContaExistenteFromList(contaList: List<Conta?>?, conta: Conta?, valor: BigDecimal?): Conta? {
        val optionalConta = getOptionalContaExistente(contaList, conta)
        return optionalConta!!.map { c: Conta? -> getContaResultAdicionaValorSaldo(c, valor) }.orElseGet { getContaResultAdicionaValorSaldo(conta, valor) }
    }

    override fun getOptionalContaExistente(contaList: List<Conta?>?, conta: Conta?): Optional<Conta?>? {
        return contaList!!.stream().filter { obj: Conta? -> Objects.nonNull(obj) }.filter { c: Conta? -> c!!.toStringNumeroContaCompleta() == conta!!.toStringNumeroContaCompleta() }.findFirst()
    }

    private fun getContaResultAdicionaValorSaldo(conta: Conta?, valor: BigDecimal?): Conta? {
        conta!!.addSaldo(valor)
        return conta
    }

    private fun montarContaFrom(csvRecord: CSVRecord): Conta {
        val strNumeroConta = csvRecord[0]
        val linhasRegistro: List<String> = ArrayList(Arrays.asList(*strNumeroConta.split(SEPARADOR_DEFAULT_CONTA.toRegex()).toTypedArray()))
        val mapParameters = getMapParametersFromListaRegistros(linhasRegistro)
        val numeroConta = java.lang.Long.valueOf(mapParameters[POSICAO_NUMERO_CONTA])
        val digitoVerificador = (mapParameters[POSICAO_DIGITO_VERIFICADOR_CONTA] ?: error("")).toInt()
        val conta = Conta()
        conta.numero = numeroConta
        conta.digito = digitoVerificador
        conta.gerarTipoConta()
        return conta
    }

    private fun getMapParametersFromListaRegistros(linhasRegistros: List<String>): Map<String, String> {
        return getStringStringMap(linhasRegistros, POSICAO_NUMERO_CONTA, POSICAO_DIGITO_VERIFICADOR_CONTA)
    }

    private fun escreveMap(mapParameters: MutableMap<String, String>, index: Int, posicao: String, value: String): String? {
        return if (isParamsPermiteEscritaMapParameters(index, posicao)) mapParameters.put(posicao, value) else mapParameters.put("", "")
    }

    private fun isParamsPermiteEscritaMapParameters(index: Int, posicao: String): Boolean {
        return index >= 0 && Objects.nonNull(posicao) && index.toString() == posicao
    }

    private fun getStringStringMap(linhasRegistro: List<String>, posicaoA: String, posicaoB: String): Map<String, String> {
        val mapParameters: MutableMap<String, String> = HashMap()
        for (i in linhasRegistro.indices) {
            escreveMap(mapParameters, i, posicaoA, linhasRegistro[i])
            escreveMap(mapParameters, i, posicaoB, linhasRegistro[i])
        }
        return mapParameters
    }

    companion object {
        private const val SEPARADOR_DEFAULT_CONTA = "-"
        private const val POSICAO_NUMERO_CONTA = "0"
        private const val POSICAO_DIGITO_VERIFICADOR_CONTA = "1"
    }
}
