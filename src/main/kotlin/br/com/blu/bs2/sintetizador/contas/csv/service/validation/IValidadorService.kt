package br.com.blu.bs2.sintetizador.contas.csv.service.validation

import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException
import java.io.File

interface IValidadorService {
    @Throws(ServiceException::class)
    fun validarPathEntradaArquivosCsv(path: String?)

    @Throws(ServiceException::class)
    fun validarContemArquivosObtidosFileDiretorioPadraoEntrada(fileInputDatList: List<File?>?)
}
