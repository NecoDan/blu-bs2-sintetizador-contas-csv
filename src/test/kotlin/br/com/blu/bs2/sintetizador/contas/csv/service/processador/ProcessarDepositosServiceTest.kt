package br.com.blu.bs2.sintetizador.contas.csv.service.processador

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo
import br.com.blu.bs2.sintetizador.contas.csv.model.enums.TipoExtensaoArquivo
import br.com.blu.bs2.sintetizador.contas.csv.utils.ArquivoUtil
import br.com.blu.bs2.sintetizador.contas.csv.utils.FormatterUtil
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil.diretorioTempSistema
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil.listaArquivosTransacao
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil.listaArquivosTransacaoRandomica
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil.salvarGravarCSV
import br.com.blu.bs2.sintetizador.contas.csv.utils.RandomicoUtil.gerarValorRandomicoLong
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.io.FileUtils
import org.junit.Before
import org.junit.Test
import java.io.File
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ProcessarDepositosServiceTest {

    private var processarDepositoService: ProcessarDepositoService? = null
    private var pathTempPadraoSistema: String? = null

    companion object {
        const val PATH_DEFAULT_SAIDA = "/out/"
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        pathTempPadraoSistema = diretorioTempSistema
        processarDepositoService = ProcessarDepositoService()
    }

    @Test
    @Throws(Exception::class)
    fun deveEfetuarProcessamentosPorSringCaminhoPathValido() {
        println("#TEST: deveEfetuarProcessamentosPorSringCaminhoPathValido: ")

        // -- 01_Cenário
        val transacaoList = listaArquivosTransacaoRandomica
        val nomeArquivo = gerarValorRandomicoLong().toString() + "." + TipoExtensaoArquivo.CSV.getCodigoLiteral()
        salvarGravarCSV(pathTempPadraoSistema, nomeArquivo, transacaoList)
        val path = "$pathTempPadraoSistema/$nomeArquivo"

        // -- 02_Ação
        processarDepositoService!!.efetuarProcessamentos(path)

        // -- 03_Verificação_Validação
        val filePathPrincipal = ParametrosTestesUtil.fileDiretorioPadraoSistema;
        val filesList = ArquivoUtil.buscarListaFiles(FileUtils.getFile(filePathPrincipal.absolutePath.plus(PATH_DEFAULT_SAIDA)), "csv")

        val fileResultFinal = filesList.stream()
                .filter { file: File? -> Objects.nonNull(file) && file!!.name.contains(nomeArquivo.replace(".csv", "")) }
                .distinct()
                .findFirst()

        assertNotNull(fileResultFinal)
        println("-------------------------------------------------------------")
    }

    @Test
    fun deveEfetuarProcessamentosPorFileCaminhoPathValido() {
        println("#TEST: deveEfetuarProcessamentosPorFileCaminhoPathValido: ")

        // -- 01_Cenário
        val transacaoList = listaArquivosTransacaoRandomica
        val nomeArquivo = gerarValorRandomicoLong().toString() + "." + TipoExtensaoArquivo.CSV.getCodigoLiteral()
        salvarGravarCSV(pathTempPadraoSistema, nomeArquivo, transacaoList)
        val filePath = FileUtils.getFile("$pathTempPadraoSistema/$nomeArquivo")

        // -- 02_Ação
        processarDepositoService!!.efetuarProcessamentosPor(filePath)

        // -- 03_Verificação_Validação
        val filePathPrincipal = ParametrosTestesUtil.fileDiretorioPadraoSistema;
        val filesList = ArquivoUtil.buscarListaFiles(FileUtils.getFile(filePathPrincipal.absolutePath.plus(PATH_DEFAULT_SAIDA)), "csv")

        val fileResultFinal = filesList.stream()
                .filter { file: File? -> Objects.nonNull(file) && file!!.name.contains(nomeArquivo.replace(".csv", "")) }
                .distinct()
                .findFirst()

        assertNotNull(fileResultFinal)
        println("-------------------------------------------------------------")
    }

    @Test
    fun deveProcessarTransacoesPorArquivo() {
        println("#TEST: deveProcessarTransacoesPorArquivo: ")

        // -- 01_Cenário
        val somaTotalSaldosRegistrosEsperado = BigDecimal.valueOf(300.00)
        val somaTotalBonusRegistrosEsperado = BigDecimal.valueOf(16.00)

        val transacaoList = listaArquivosTransacao
        val nomeArquivo = gerarValorRandomicoLong().toString() + "." + TipoExtensaoArquivo.CSV.getCodigoLiteral()
        salvarGravarCSV(pathTempPadraoSistema, nomeArquivo, transacaoList)
        val filePath = FileUtils.getFile("$pathTempPadraoSistema/$nomeArquivo")

        val arquivo = Arquivo()
        arquivo.fileEntrada = filePath
        arquivo.nome = nomeArquivo;
        arquivo.pathCompleto = filePath.absolutePath

        // -- 02_Ação
        processarDepositoService!!.processarTransacoesPorArquivo(arquivo)

        // -- 03_Verificação_Validação
        val filePathPrincipal = ParametrosTestesUtil.fileDiretorioPadraoSistema;
        val filesList = ArquivoUtil.buscarListaFiles(FileUtils.getFile(filePathPrincipal.absolutePath.plus(PATH_DEFAULT_SAIDA)), "csv")

        val fileResultFinal = filesList.stream()
                .filter { file: File? -> Objects.nonNull(file) && file!!.name.contains(nomeArquivo.replace(".csv", "")) }
                .distinct()
                .findFirst()

        var somaTotalSaldosRegistros = BigDecimal.valueOf(0)
        var somaTotalBonusRegistros = BigDecimal.valueOf(0)

        Files.newBufferedReader(Paths.get(fileResultFinal.get().absolutePath)).use { reader ->
            CSVParser(reader, CSVFormat.DEFAULT.withHeader().withDelimiter(';').withIgnoreHeaderCase().withTrim()).use { csvParser ->
                for (csvRecord in csvParser) {
                    somaTotalSaldosRegistros = somaTotalSaldosRegistros.add(FormatterUtil.onlyBigDecimal(csvRecord[1]))
                    somaTotalBonusRegistros = somaTotalBonusRegistros.add(FormatterUtil.onlyBigDecimal(csvRecord[2]))
                }
            }
        }

        assertTrue(isEquals(somaTotalSaldosRegistrosEsperado, somaTotalSaldosRegistros) && isEquals(somaTotalBonusRegistrosEsperado, somaTotalBonusRegistros))
        println("-------------------------------------------------------------")
    }

    private fun isEquals(valor1: BigDecimal, valor2: BigDecimal?): Boolean {
        return Objects.nonNull(valor1) && Objects.nonNull(valor2) && valor1.compareTo(valor2) == 0
    }
}
