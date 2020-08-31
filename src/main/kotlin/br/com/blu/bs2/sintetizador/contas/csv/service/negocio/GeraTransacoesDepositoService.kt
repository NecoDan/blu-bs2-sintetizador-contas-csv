package br.com.blu.bs2.sintetizador.contas.csv.service.negocio

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.ExtratoConta
import br.com.blu.bs2.sintetizador.contas.csv.model.enums.TipoExtensaoArquivo
import br.com.blu.bs2.sintetizador.contas.csv.service.strategy.PercentualBonus
import br.com.blu.bs2.sintetizador.contas.csv.utils.ArquivoUtil.criarPathDiretorioInexistente
import br.com.blu.bs2.sintetizador.contas.csv.utils.FormatterUtil.formatarNumero
import br.com.blu.bs2.sintetizador.contas.csv.utils.FormatterUtil.toStringLocalDateFormatada
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.io.FileUtils
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDateTime
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

class GeraTransacoesDepositoService : IGeraTransacoesDepositoService {

    @Throws(ServiceException::class)
    override fun gerarDepositosFromTransacoesDasContas(arquivo: Arquivo?, contaList: List<Conta?>?): Arquivo? {
        if (Objects.isNull(contaList) || contaList!!.isEmpty())
            throw ServiceException("Não existem contas a serem processadas ou a efetuar novas transações de depósitos.")

        val extratoContaList = contaList.stream().filter { obj: Conta? -> Objects.nonNull(obj) }.map { conta: Conta? -> montarExtratoContaFrom(conta) }.collect(Collectors.toList())
        return salvarExtratosContas(arquivo, extratoContaList)
    }

    @Throws(ServiceException::class)
    override fun salvarExtratosContas(arquivo: Arquivo?, extratoContaList: List<ExtratoConta?>?): Arquivo? {
        return try {
            val nomeArquivo = getNomeNovoArquivo(arquivo)
            val strPathArquivoSaida = arquivo!!.fileEntrada!!.parent + "/" + PATH_DEFAULT_SAIDA
            val fileNovoDiretorioSaidaArquivo = criarPathDiretorioInexistente(FileUtils.getFile(strPathArquivoSaida))
            val fileSaida = FileUtils.getFile("$strPathArquivoSaida/$nomeArquivo")
            val conteudo = gravarExtraoContaCSVSaidaRelatorio(fileNovoDiretorioSaidaArquivo.absolutePath, nomeArquivo, extratoContaList)
            arquivo.fileSaida = fileSaida
            arquivo.conteudo = conteudo
            arquivo
        } catch (e: IOException) {
            throw ServiceException("Erro ao gerar o arquivo de saída: " + e.localizedMessage)
        }
    }

    private fun montarExtratoContaFrom(conta: Conta?): ExtratoConta {
        val extratoConta = ExtratoConta()
        extratoConta.conta = conta
        extratoConta.valorTotalDeposito = conta!!.getSaldo()
        extratoConta.aplicarValorPercentualBonus(PercentualBonus.of(conta.digito!!))

        return extratoConta
    }

    @Throws(IOException::class)
    private fun gravarExtraoContaCSVSaidaRelatorio(pahtCaminhoASalvar: String, nomeArquivo: String, extratoContaList: List<ExtratoConta?>?): String {
        val stringBuilder = StringBuilder()

        FileWriter("$pahtCaminhoASalvar/$nomeArquivo").use { writer ->
            CSVPrinter(stringBuilder, CSVFormat.DEFAULT.withHeader(HEADER_COL_RELATORIO_POS_0, HEADER_COL_RELATORIO_POS_1, HEADER_COL_RELATORIO_POS_2).withDelimiter(';')).use { csvPrinter ->
                extratoContaList!!.forEach(Consumer { extratoConta: ExtratoConta? ->
                    try {
                        csvPrinter.printRecord(
                                extratoConta!!.conta!!.toStringNumeroContaCompleta(),
                                formatarNumero(extratoConta.valorTotalDeposito!!),
                                formatarNumero(extratoConta.valorPercentualBonus!!)
                        )
                    } catch (e: IOException) {
                        println(e.localizedMessage)
                    }
                })
                writer.write(stringBuilder.toString())
                writer.flush()
            }
        }

        return stringBuilder.toString()
    }

    private fun getNomeNovoArquivo(arquivo: Arquivo?): String {
        return arquivo!!.fileEntrada!!.name
                .replace(".", "_")
                .toUpperCase()
                .plus("_SAIDA_" + toStringLocalDateFormatada(LocalDateTime.now()) + "." + TipoExtensaoArquivo.CSV.getCodigoLiteral())
    }

    companion object {
        private const val HEADER_COL_RELATORIO_POS_0 = "Conta"
        private const val HEADER_COL_RELATORIO_POS_1 = "Depósitos"
        private const val HEADER_COL_RELATORIO_POS_2 = "Total de Bônus"
        private const val PATH_DEFAULT_SAIDA = "out"
    }
}
