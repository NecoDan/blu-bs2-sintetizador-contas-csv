package br.com.blu.bs2.sintetizador.contas.csv.service.strategy;

import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.TipoConta;

import java.util.Objects;
import java.util.stream.Stream;

public enum PercentualBonus {

    BONUS_CONTA_NAO_ESPECIFICADA(TipoConta.SEM_ESPECIFICACAO, new BuilderBonusContaNaoEspecificada()),

    BONUS_CONTA_COMUM(TipoConta.COMUM, new BuilderBonusContaComumService()),

    BONUS_CONTA_PREMIUM(TipoConta.PREMIUM, new BuilderBonusContaPremiumService()),

    BONUS_CONTA_EXECUTIVA(TipoConta.EXECUTIVA, new BuilderBonusContaExecutivaService());

    private final TipoConta tipoConta;
    private final IFactoryObterPercentualBonusService factoryObterPercentualBonusService;

    PercentualBonus(TipoConta tipoConta, IFactoryObterPercentualBonusService factoryObterPercentualBonusService) {
        this.tipoConta = tipoConta;
        this.factoryObterPercentualBonusService = factoryObterPercentualBonusService;
    }

    public static PercentualBonus of(int digitoVerificador) {
        return Stream.of(PercentualBonus.values())
                .filter(percentualBonus -> Objects.nonNull(percentualBonus.getTipoConta()))
                .filter(percentualBonus -> percentualBonus.getTipoConta().getDigitosVerificador().contains(digitoVerificador))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public IFactoryObterPercentualBonusService getFactoryObterPercentualBonusService() {
        return this.factoryObterPercentualBonusService;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }
}
