package br.com.blu.bs2.sintetizador.contas.csv.service.file

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo
import br.com.blu.bs2.sintetizador.contas.csv.model.enums.TipoExtensaoArquivo
import br.com.blu.bs2.sintetizador.contas.csv.service.validation.IValidadorService
import br.com.blu.bs2.sintetizador.contas.csv.service.validation.ValidadorService
import br.com.blu.bs2.sintetizador.contas.csv.utils.ArquivoUtil.buscarListaFiles
import br.com.blu.bs2.sintetizador.contas.csv.utils.ArquivoUtil.isNotContainsArquivosFileDiretorio
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.*
import java.util.stream.Collectors

class ArquivoService : IArquivoService {

    val validadorService: IValidadorService

    @Throws(ServiceException::class)
    override fun recuperarArquivosCsvFromPath(path: String?): List<Arquivo?>? {
        validadorService.validarPathEntradaArquivosCsv(path)
        return recuperarArquivosCsvFromFilePath(FileUtils.getFile(path))
    }

    @Throws(ServiceException::class)
    override fun recuperarArquivosCsvFromFilePath(filePath: File?): List<Arquivo?>? {
        val fileList = if (filePath!!.isDirectory) findAllFiles(filePath) else listOf<File?>(filePath)
        return fileList!!.stream().filter { obj: File? -> Objects.nonNull(obj) }.map { file: File? -> mountArquivoCsvFromFile(file) }.collect(Collectors.toList())
    }

    @Throws(ServiceException::class)
    override fun findAllFilesPor(filePath: String?): List<File?>? {
        return findAllFiles(FileUtils.getFile(filePath))
    }

    @Throws(ServiceException::class)
    override fun findAllFiles(filePath: File?): List<File?>? {
        val extensaoCSVDefault = TipoExtensaoArquivo.CSV.getCodigoLiteral()
        val fileInputCsvList = procurar(filePath, extensaoCSVDefault)
        validadorService.validarContemArquivosObtidosFileDiretorioPadraoEntrada(fileInputCsvList)
        return fileInputCsvList
    }

    fun mountArquivoCsvFromFile(file: File?): Arquivo {
        val arquivo = Arquivo()
        arquivo.fileEntrada = file
        arquivo.path = file!!.path
        arquivo.nome = file.name
        arquivo.pathCompleto = file.absolutePath
        return arquivo
    }

    private fun procurar(filePathInputDefault: File?, extensaoArquivoBusca: String): List<File?> {
        return if (isNotContainsArquivosDiretorioPadraoEntrada(filePathInputDefault)) emptyList<File>() else buscarListaFiles(filePathInputDefault, extensaoArquivoBusca)
    }

    private fun isNotContainsArquivosDiretorioPadraoEntrada(filePathInput: File?): Boolean {
        return isNotContainsArquivosFileDiretorio(filePathInput!!)
    }

    init {
        validadorService = ValidadorService()
    }
}
