package br.com.blu.bs2.sintetizador.contas.csv

import br.com.blu.bs2.sintetizador.contas.csv.service.processador.IProcessarDepositoService
import br.com.blu.bs2.sintetizador.contas.csv.service.processador.ProcessarDepositoService
import java.util.*
import kotlin.system.exitProcess

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        val processarDepositoService: IProcessarDepositoService = ProcessarDepositoService()
        try {
            println("#... Sintetizador Transferências Depósitos ...#")
            val inPathDiretorio = Scanner(System.`in`)
            println("\n Defina a pasta e/ou diretorio com o(s) arquivo(s) CSV a serem processados: ")
            val path = inPathDiretorio.nextLine()
            processarDepositoService.efetuarProcessamentos(path)
        } catch (e: Exception) {
            println("Houve erro ao procesar arquivo e gerar depósitos: " + e.localizedMessage)
        }
        exitProcess(0)
    }
}
