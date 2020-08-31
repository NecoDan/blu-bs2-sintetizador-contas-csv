package br.com.blu.bs2.sintetizador.contas.csv.service.negocio;

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo;
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta;
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.ExtratoConta;
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException;

import java.util.List;

public interface IGeraTransacoesDepositoService {
    Arquivo gerarDepositosFromTransacoes(Arquivo arquivo, List<Conta> transacaoList) throws ServiceException;

    Arquivo salvarExtratosContas(Arquivo arquivo, List<ExtratoConta> extratoContaList) throws ServiceException;
}
