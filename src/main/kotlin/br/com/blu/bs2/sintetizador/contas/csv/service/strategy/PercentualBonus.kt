package br.com.blu.bs2.sintetizador.contas.csv.service.strategy

import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.TipoConta

enum class PercentualBonus(val tipoConta: TipoConta, val factoryObterPercentualBonusService: IFactoryObterPercentualBonusService) {

    BONUS_CONTA_NAO_ESPECIFICADA(TipoConta.SEM_ESPECIFICACAO, BuilderBonusContaNaoEspecificada()),

    BONUS_CONTA_COMUM(TipoConta.COMUM, BuilderBonusContaComumService()),

    BONUS_CONTA_PREMIUM(TipoConta.PREMIUM, BuilderBonusContaPremiumService()),

    BONUS_CONTA_EXECUTIVA(TipoConta.EXECUTIVA, BuilderBonusContaExecutivaService());

    companion object {
        @Throws(IllegalArgumentException::class)
        fun of(digitoVerificador: Int): PercentualBonus {
            var percentualBonusResult = BONUS_CONTA_NAO_ESPECIFICADA

            for (percentualBonus in values()) {
                if (percentualBonus.tipoConta.digitosVerificador!!.contains(digitoVerificador))
                    percentualBonusResult = percentualBonus
            }

            return percentualBonusResult
        }
    }
}
