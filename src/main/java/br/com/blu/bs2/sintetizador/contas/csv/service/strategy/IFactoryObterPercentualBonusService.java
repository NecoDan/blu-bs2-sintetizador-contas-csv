package br.com.blu.bs2.sintetizador.contas.csv.service.strategy;


import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta;

import java.math.BigDecimal;

public interface IFactoryObterPercentualBonusService {

    boolean isAppliable(Conta conta);

    BigDecimal obterValorPercentualBonusAoDeposito(Conta conta);

    PercentualBonus getPercentualBonus(Conta conta);
}
