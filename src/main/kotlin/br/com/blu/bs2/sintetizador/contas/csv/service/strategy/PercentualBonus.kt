package br.com.blu.bs2.sintetizador.contas.csv.service.strategy

import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.TipoConta
import java.util.*
import java.util.stream.Stream

enum class PercentualBonus(val tipoConta: TipoConta, val factoryObterPercentualBonusService: IFactoryObterPercentualBonusService) {

    BONUS_CONTA_NAO_ESPECIFICADA(TipoConta.SEM_ESPECIFICACAO, BuilderBonusContaNaoEspecificada()),

    BONUS_CONTA_COMUM(TipoConta.COMUM, BuilderBonusContaComumService()),

    BONUS_CONTA_PREMIUM(TipoConta.PREMIUM, BuilderBonusContaPremiumService()),

    BONUS_CONTA_EXECUTIVA(TipoConta.EXECUTIVA, BuilderBonusContaExecutivaService());

    companion object {
//        fun of(digitoVerificador: Int): PercentualBonus {
//            return Stream.of(*values())
//                    .filter { percentualBonus: PercentualBonus -> Objects.nonNull(percentualBonus.tipoConta) }
//                    .filter { percentualBonus: PercentualBonus -> percentualBonus.tipoConta.digitosVerificador!!.contains(digitoVerificador) }
//                    .findFirst()
//                    .orElseThrow { IllegalArgumentException() }
//        }
    }
}
