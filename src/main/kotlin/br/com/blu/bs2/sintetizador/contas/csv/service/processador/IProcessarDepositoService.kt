package br.com.blu.bs2.sintetizador.contas.csv.service.processador

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException
import java.io.File
import java.io.IOException

interface IProcessarDepositoService {

    @Throws(Exception::class)
    fun efetuarProcessamentos(caminhoArquivos: String?)

    @Throws(Exception::class)
    fun efetuarProcessamentosPor(fileCaminhoArquivos: File?)

    @Throws(IOException::class, ServiceException::class)
    fun processarTransacoesPorArquivo(arquivo: Arquivo?): Arquivo?
}
