package br.com.blu.bs2.sintetizador.contas.csv.utils

import br.com.blu.bs2.sintetizador.contas.csv.model.Transacao
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.io.IOException
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.function.Consumer

object ParametrosTestesUtil {

    val diretorioTempSistema: String
        get() {
            val property = "java.io.tmpdir"
            val tempDir = System.getProperty(property)
            println("Diretorio temporario: $tempDir")
            return tempDir
        }

    val fileDiretorioPadraoSistema: File
        get() = File(diretorioTempSistema)

    private fun mountTransacaoFrom(numeroConta: String?, valor: BigDecimal?): Transacao {
        val transacao = Transacao()
        transacao.numeroConta = numeroConta
        transacao.valorTransacao = valor
        return transacao
    }

    val listaArquivosTransacao: List<Transacao>
        get() = listOf(
                mountTransacaoFrom("1234-3", BigDecimal.valueOf(75)),
                mountTransacaoFrom("1234-3", BigDecimal.valueOf(25)),
                mountTransacaoFrom("1235-6", BigDecimal.valueOf(100)),
                mountTransacaoFrom("1236-0", BigDecimal.valueOf(100))
        )

    private fun isPathCaminhoASalvarInvalido(pahtCaminhoASalvar: String): Boolean {
        return Objects.isNull(pahtCaminhoASalvar) || pahtCaminhoASalvar.isEmpty()
    }

    @Throws(IOException::class)
    fun salvarGravarCSV(pahtCaminhoASalvar: String?, nomeArquivo: String, transacaoList: List<Transacao>) {
        Files
                .newBufferedWriter(Paths.get(if (pahtCaminhoASalvar?.let { isPathCaminhoASalvarInvalido(it) }!!) diretorioTempSistema else "$pahtCaminhoASalvar/$nomeArquivo")).use { writer ->
                    CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Conta", "Valor").withDelimiter(';')).use { csvPrinter ->
                        transacaoList.forEach(Consumer { transacao: Transacao ->
                            try {
                                csvPrinter.printRecord(transacao.numeroConta, transacao.valorTransacao.toString())
                            } catch (e: IOException) {
                                println(e.localizedMessage)
                            }
                        })
                        csvPrinter.flush()
                    }
                }
    }
}
