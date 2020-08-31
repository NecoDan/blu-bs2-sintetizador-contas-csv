package br.com.blu.bs2.sintetizador.contas.csv.utils

import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.FileFilterUtils
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

/**
 * @author Daniel Santos
 */
object ArquivoUtil {

    private const val PATH_DEFAULT_SAIDA = "data/out"

    fun isNotContainsArquivosFileDiretorio(file: File): Boolean {
        return isFileInValido(file) || Objects.isNull(file.listFiles()) || Objects.nonNull(file.listFiles()) && Objects.requireNonNull(file.listFiles()).isEmpty()
    }

    fun isFileInValido(file: File): Boolean {
        return !isFileValido(file)
    }

    fun isFileValido(file: File): Boolean {
        return Objects.nonNull(file) && file.exists() && file.canRead()
    }

    fun buscarListaFiles(filePath: File?, extensaoArquivoFiltro: String): List<File> {
        return (if (isNotContainsExtensaoComoFiltroBusca(extensaoArquivoFiltro)) FileUtils.listFiles(filePath, FileFilterUtils.fileFileFilter(), null) else FileUtils.listFiles(filePath, FileFilterUtils.suffixFileFilter(extensaoArquivoFiltro), null)) as List<File>
    }

    private fun isNotContainsExtensaoComoFiltroBusca(extensaoArquivoFiltro: String): Boolean {
        return Objects.isNull(extensaoArquivoFiltro) || extensaoArquivoFiltro.isEmpty()
    }

    fun criarPathDiretorioInexistente(filePathDiretorio: File): File {
        if (!filePathDiretorio.exists()) {
            filePathDiretorio.mkdir()
        }
        return filePathDiretorio
    }
}
