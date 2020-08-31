package br.com.blu.bs2.sintetizador.contas.csv.service.processador

import br.com.blu.bs2.sintetizador.contas.csv.model.enums.TipoExtensaoArquivo
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil.diretorioTempSistema
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil.listaArquivosTransacao
import br.com.blu.bs2.sintetizador.contas.csv.utils.ParametrosTestesUtil.salvarGravarCSV
import br.com.blu.bs2.sintetizador.contas.csv.utils.RandomicoUtil.gerarValorRandomicoLong
import org.junit.Before
import org.junit.Test

class ProcessarDepositosServiceTest {

    private var processarDepositoService: ProcessarDepositoService? = null
    private var pathTempPadraoSistema: String? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        pathTempPadraoSistema = diretorioTempSistema
        processarDepositoService = ProcessarDepositoService()
    }

    @Test
    @Throws(Exception::class)
    fun efetuarProcessamentosPorSringCaminhoPathValido() {
        println("#TEST: efetuarProcessamentosPorSringCaminhoPathValido: ")

        // -- 01_Cenário
        val transacaoList = listaArquivosTransacao
        val nomeArquivo = gerarValorRandomicoLong().toString() + "." + TipoExtensaoArquivo.CSV.getCodigoLiteral()
        salvarGravarCSV(pathTempPadraoSistema, nomeArquivo, transacaoList)
        val path = "$pathTempPadraoSistema/$nomeArquivo"

        // -- 02_Ação
        processarDepositoService!!.efetuarProcessamentos(path)

        // -- 03_Verificação_Validação
        //  System.out.println("Contains arquivos a serem lidos no diretorio: " + fileList.size());
        println("-------------------------------------------------------------")
    }

    @Test
    fun efetuarProcessamentosPorFileCaminhoPathValido() {
    }
}
