package br.com.blu.bs2.sintetizador.contas.csv.service.strategy;

import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta;

import java.math.BigDecimal;
import java.util.Objects;

public class BuilderBonusContaPremiumService implements IFactoryObterPercentualBonusService {

    private static final double VALOR_FIXO_DEFAUL_PERCENTUAL_DEZ_PORCENTO = 10.00;

    @Override
    public boolean isAppliable(Conta conta) {
        return (Objects.nonNull(conta) && conta.isTipoContaIsPremium());
    }

    @Override
    public BigDecimal obterValorPercentualBonusAoDeposito(Conta conta) {
        return (isAppliable(conta)) ? BigDecimal.valueOf(VALOR_FIXO_DEFAUL_PERCENTUAL_DEZ_PORCENTO) : BigDecimal.ZERO;
    }

    @Override
    public PercentualBonus getPercentualBonus(Conta conta) {
        return (isAppliable(conta)) ? PercentualBonus.BONUS_CONTA_PREMIUM : PercentualBonus.BONUS_CONTA_NAO_ESPECIFICADA;
    }
}
