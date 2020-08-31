package br.com.blu.bs2.sintetizador.contas.csv.service.processador

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo
import br.com.blu.bs2.sintetizador.contas.csv.service.file.ArquivoService
import br.com.blu.bs2.sintetizador.contas.csv.service.file.IArquivoService
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.FactoryContas
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.GeraTransacoesDepositoService
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.IFactoryContas
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.IGeraTransacoesDepositoService
import br.com.blu.bs2.sintetizador.contas.csv.service.validation.IValidadorService
import br.com.blu.bs2.sintetizador.contas.csv.service.validation.ValidadorService
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.util.*

class ProcessarDepositoService : IProcessarDepositoService {

    private val arquivoService: IArquivoService
    private val geraTransacoesDepositoService: IGeraTransacoesDepositoService
    private val factoryContas: IFactoryContas
    private val validadorService: IValidadorService

    @Throws(Exception::class)
    override fun efetuarProcessamentos(caminhoArquivos: String?) {
        validadorService.validarPathEntradaArquivosCsv(caminhoArquivos)
        efetuarProcessamentosPor(FileUtils.getFile(caminhoArquivos))
    }

    @Throws(Exception::class)
    override fun efetuarProcessamentosPor(fileCaminhoArquivos: File?) {
        val arquivoList = arquivoService.recuperarArquivosCsvFromFilePath(fileCaminhoArquivos)
        if (isNaoContemArquivosAProcessar(arquivoList)) return
        arquivoList!!.parallelStream().filter { obj: Arquivo? -> Objects.nonNull(obj) }.forEach { arquivo: Arquivo? ->
            try {
                val arquivoResult = processarTransacoesPorArquivo(arquivo)
                if (arquivoResult != null) {
                    imprimirResultados(arquivoResult)
                }
            } catch (e: IOException) {
                throw RuntimeException(e.localizedMessage)
            } catch (e: ServiceException) {
                throw RuntimeException(e.localizedMessage)
            }
        }
    }

    @Throws(IOException::class, ServiceException::class)
    private fun processarTransacoesPorArquivo(arquivo: Arquivo?): Arquivo? {
        val contaList = factoryContas.getContasPorArquivoEFileCSVReader(arquivo)
        return geraTransacoesDepositoService.gerarDepositosFromTransacoesDasContas(arquivo, contaList)
    }

    private fun isNaoContemArquivosAProcessar(arquivoList: List<Arquivo?>?): Boolean {
        return Objects.isNull(arquivoList) || arquivoList!!.isEmpty()
    }

    private fun imprimirResultados(arquivo: Arquivo) {
        if (Objects.isNull(arquivo))
            return
        println("""
# Arquivo do {RELATÓRIO} de {SAIDA} gerado com sucesso: ${arquivo.fileSaida!!.absolutePath}""")
        println("# REPORT: \n")
        println(arquivo.conteudo)
    }

    init {
        arquivoService = ArquivoService()
        geraTransacoesDepositoService = GeraTransacoesDepositoService()
        factoryContas = FactoryContas()
        validadorService = ValidadorService()
    }
}
