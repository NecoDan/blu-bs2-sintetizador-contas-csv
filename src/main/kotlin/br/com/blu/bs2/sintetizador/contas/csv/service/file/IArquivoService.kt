package br.com.blu.bs2.sintetizador.contas.csv.service.file

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException
import java.io.File

interface IArquivoService {
    @Throws(ServiceException::class)
    fun recuperarArquivosCsvFromPath(path: String?): List<Arquivo?>?

    @Throws(ServiceException::class)
    fun recuperarArquivosCsvFromFilePath(path: File?): List<Arquivo?>?

    @Throws(ServiceException::class)
    fun findAllFiles(filePath: File?): List<File?>?

    @Throws(ServiceException::class)
    fun findAllFilesPor(filePath: String?): List<File?>?
}
