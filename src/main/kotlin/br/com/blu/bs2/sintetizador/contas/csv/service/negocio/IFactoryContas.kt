package br.com.blu.bs2.sintetizador.contas.csv.service.negocio

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta
import java.io.IOException
import java.math.BigDecimal
import java.util.*

interface IFactoryContas {

    @Throws(IOException::class)
    fun getContasPorArquivoEFileCSVReader(arquivo: Arquivo?): List<Conta?>?

    fun getContaExistenteFromList(contaList: List<Conta?>?, conta: Conta?, valor: BigDecimal?): Conta?

    fun getOptionalContaExistente(contaList: List<Conta?>?, conta: Conta?): Optional<Conta?>?
}
