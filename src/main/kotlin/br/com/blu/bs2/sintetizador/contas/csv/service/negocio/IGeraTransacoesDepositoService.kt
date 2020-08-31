package br.com.blu.bs2.sintetizador.contas.csv.service.negocio

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.ExtratoConta
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException

interface IGeraTransacoesDepositoService {

    @Throws(ServiceException::class)
    fun gerarDepositosFromTransacoesDasContas(arquivo: Arquivo?, transacaoList: List<Conta?>?): Arquivo?

    @Throws(ServiceException::class)
    fun salvarExtratosContas(arquivo: Arquivo?, extratoContaList: List<ExtratoConta?>?): Arquivo?
}
