package br.com.blu.bs2.sintetizador.contas.csv.service.strategy;

import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta;

import java.math.BigDecimal;

public class BuilderBonusContaNaoEspecificada implements IFactoryObterPercentualBonusService {

    private static final double VALOR_FIXO_DEFAUL_PERCENTUAL_ZERADO = 0.0;

    @Override
    public boolean isAppliable(Conta conta) {
        return false;
    }

    @Override
    public BigDecimal obterValorPercentualBonusAoDeposito(Conta conta) {
        return BigDecimal.valueOf(VALOR_FIXO_DEFAUL_PERCENTUAL_ZERADO);
    }

    @Override
    public PercentualBonus getPercentualBonus(Conta conta) {
        return PercentualBonus.BONUS_CONTA_NAO_ESPECIFICADA;
    }
}
