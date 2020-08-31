package br.com.blu.bs2.sintetizador.contas.csv.service.validation

import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException
import java.io.File
import java.util.*

class ValidadorService : IValidadorService {
    @Throws(ServiceException::class)
    override fun validarPathEntradaArquivosCsv(path: String?) {
        if (Objects.isNull(path) || path!!.isEmpty()) throw ServiceException("Parametro com o caminho do diretório de entrada dos arquivos, encontra-se inválido (conteúdo vazio) ou inexistente {NULL}.")
    }

    @Throws(ServiceException::class)
    override fun validarContemArquivosObtidosFileDiretorioPadraoEntrada(fileInputDatList: List<File?>?) {
        if (fileInputDatList!!.isEmpty()) throw ServiceException("Não foram encontrados arquivos de leitura com a extensão {CSV} para entrada de dados e/ou pasta vazia.")
    }
}
