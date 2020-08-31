package br.com.blu.bs2.sintetizador.contas.csv.service.arquivo

import br.com.blu.bs2.sintetizador.contas.csv.model.enums.TipoExtensaoArquivo
import br.com.blu.bs2.sintetizador.contas.csv.service.file.ArquivoService
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil.diretorioTempSistema
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil.listaArquivosTransacao
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil.salvarGravarCSV
import br.com.blu.bs2.sintetizador.contas.csv.utils.RandomicoUtil.gerarValorRandomicoLong
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ArquivoServiceTest {

    private var arquivoService: ArquivoService? = null
    private var diretorioTemporarioSistema: String? = null

    @Before
    fun setUp() {
        arquivoService = ArquivoService()
        diretorioTemporarioSistema = diretorioTempSistema
    }

    @Test
    @Throws(IOException::class, ServiceException::class)
    fun deveRetornarListaFilesArquivosCsvAPartiCaminhoParametro() {
        println("#TEST: deveRetornarListaFilesArquivosCsvAPartiCaminhoParametro: ")

        // -- 01_Cenário
        val transacaoList = listaArquivosTransacao
        val nomeArquivo = gerarValorRandomicoLong().toString() + "." + TipoExtensaoArquivo.CSV.getCodigoLiteral()
        salvarGravarCSV(diretorioTemporarioSistema, nomeArquivo, transacaoList)

        // -- 02_Ação
        val fileList = arquivoService!!.findAllFilesPor(diretorioTemporarioSistema)

        // -- 03_Verificação_Validação
        Assert.assertTrue(fileList!!.isNotEmpty())
        println("Contains arquivos a serem lidos no diretorio: " + fileList.size)
        println("-------------------------------------------------------------")
    }

    @Test
    @Throws(IOException::class, ServiceException::class)
    fun deveRetornarUmaListaObjsArquivosCSVAPartiCaminhoParametro() {
        println("#TEST: deveRetornarUmaListaObjsArquivosCSVAPartiCaminhoParametro: ")

        // -- 01_Cenário
        val transacaoList = listaArquivosTransacao
        val nomeArquivo = gerarValorRandomicoLong().toString() + "." + TipoExtensaoArquivo.CSV.getCodigoLiteral()
        salvarGravarCSV(diretorioTemporarioSistema, nomeArquivo, transacaoList)

        // -- 02_Ação
        val arquivoList = arquivoService!!.recuperarArquivosCsvFromPath(diretorioTemporarioSistema)

        // -- 03_Verificação_Validação
        Assert.assertTrue(arquivoList!!.isNotEmpty())
        println("Contains arquivos a serem lidos no diretorio: " + arquivoList.size)
        println("-------------------------------------------------------------")
    }
}
