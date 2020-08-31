package br.com.blu.bs2.sintetizador.contas.csv.service.processador

import java.io.File

interface IProcessarDepositoService {
    @Throws(Exception::class)
    fun efetuarProcessamentos(caminhoArquivos: String?)

    @Throws(Exception::class)
    fun efetuarProcessamentosPor(fileCaminhoArquivos: File?)
}
